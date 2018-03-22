package hu.blackbelt.judo.generator.maven.plugin.execute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import hu.blackbelt.judo.generator.maven.plugin.AbstractEpsilonMojo;
import hu.blackbelt.judo.generator.maven.plugin.EmfModelUtils;
import hu.blackbelt.judo.generator.maven.plugin.Model;
import hu.blackbelt.judo.generator.utils.AbbreviateUtils;
import hu.blackbelt.judo.generator.utils.MD5Utils;
import hu.blackbelt.judo.generator.utils.UUIDUtils;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.tools.EmfTool;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelReference;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.profiling.Profiler;
import org.eclipse.epsilon.profiling.ProfilerTargetSummary;
import org.eclipse.epsilon.profiling.ProfilingExecutionListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mojo(
        name = "execute",
        defaultPhase = LifecyclePhase.GENERATE_RESOURCES
)
public class ExecuteEpsilonMojo extends AbstractEpsilonMojo {

    @Parameter(name = "eolPrograms", readonly = true, required = true)
    public List<Eol> eolPrograms;

    @Parameter(name = "profile", readonly = true, required = false)
    public Boolean profile = false;

    public ExecuteEpsilonMojo() {
    }
    synchronized public void execute() throws MojoExecutionException, MojoFailureException {
        Map<Model, EmfModel> emfModels = Maps.newConcurrentMap();
        ResourceSet resourceSet = EmfModelUtils.initResourceSet();
        ModelRepository projectModelRepository = new ModelRepository();
        Map<Object, Object> context = new HashMap();
        
        try {
            Exception ex = null;
            try {
                addMetaModels(resourceSet);
                addModels(resourceSet, projectModelRepository, emfModels);

                getLog().info("URL converters: \n\t" + URIConverter.URI_MAP.entrySet().stream().map(e -> e.getKey() + "->" + e.getValue()).collect(Collectors.joining("\n\t")));

                if (eolPrograms != null) {
                    for (Eol eolProgram : eolPrograms) {

                        IEolExecutableModule eolModule = eolProgram.getModule(context);
                        
                        // Determinate any mode have alias or not
                        boolean isAliasExists = false;
                        for (Model model : emfModels.keySet()) {
                            if (model.getAliases() != null) {
                            	isAliasExists = true;
                            }
                        }

                        if (isAliasExists) {
                            ModelRepository repository = eolModule.getContext().getModelRepository();

                            for (Model model : emfModels.keySet()) {
                                ModelReference ref = EmfModelUtils.createModelReference(emfModels.get(model));
                                ref.setName(model.getName());
                                if (model.getAliases() != null) {
                                    for (String alias : model.getAliases()) {
                                        ref.getAliases().add(alias);
                                    }
                                }
                                repository.addModel(ref);
                            }
                        } else {
                        	eolModule.getContext().setModelRepository(projectModelRepository);
                        }

                        List<EolProgramParameter> params = eolProgram.parameters;
                        if (params == null) {
                            params = Lists.newArrayList();
                        }
                                                
                        getLog().info("Running program: " + eolProgram.source);

                        executeModule(eolModule, eolProgram.source,
                                params.stream().map(p -> Variable.createReadOnlyVariable(p.name, p.value)).collect(Collectors.toList()));

                        eolProgram.post(context);
                        
                        if (!eolProgram.isOk()) {
                            throw new MojoExecutionException("Program aborted: " + eolProgram.toString());
                        } else {
                            getLog().info("Execution result: " + eolProgram.toString());
                        }
                    }
                }
            } catch (Exception e) {
                ex = e;
                getLog().error("Error", e);
            } finally {
                if (ex != null) {
                    for (IModel model : projectModelRepository.getModels()) {
                        model.setStoredOnDisposal(false);
                    }
                }
                projectModelRepository.dispose();
                if (ex != null) {
                    throw new MojoExecutionException("Could not run", ex);
                }
            }
        } finally {
        }
    }

    private void executeModule(IEolExecutableModule eolModule, File source, List<Variable> parameters) throws Exception {
        eolModule.parse(source);
        if (profile) {
            Profiler.INSTANCE.reset();
        }
        try {
            if (profile) {
                Profiler.INSTANCE.start(source.getName(), "", eolModule);
            }

            if (eolModule.getParseProblems().size() > 0) {
                getLog().error("Parse errors occured...");
                for (ParseProblem problem : eolModule.getParseProblems()) {
                    getLog().error(problem.toString());
                }
                throw new MojoExecutionException("Parse error");
            }
            for (Variable parameter : parameters) {
            	// Adding static utils
            	eolModule.getContext().getFrameStack().put(Variable.createReadOnlyVariable("UUIDUtils", new UUIDUtils()));
            	eolModule.getContext().getFrameStack().put(Variable.createReadOnlyVariable("MD5Utils", new MD5Utils()));
            	eolModule.getContext().getFrameStack().put(Variable.createReadOnlyVariable("AbbreviateUtils", new AbbreviateUtils()));
            	eolModule.getContext().getFrameStack().put(Variable.createReadOnlyVariable("EMFTool", new EmfTool()));
            	eolModule.getContext().getFrameStack().put(parameter);
            }

            if (profile) {
                eolModule.getContext().getExecutorFactory().addExecutionListener(new ProfilingExecutionListener());
            }

            Object result = eolModule.execute();
            // getLog().info("Eol execute result: " + result.toString());
        } finally {
            if (profile) {
                Profiler.INSTANCE.stop(source.getName());
                for (ProfilerTargetSummary p : Profiler.INSTANCE.getTargetSummaries()) {
                    getLog().info(String.format("Index: %d Name: %s: Count: %d Individual Time: %d Aggregate time: %d", p.getIndex(), p.getName(), p.getExecutionCount(), p.getExecutionTime().getIndividual(), p.getExecutionTime().getAggregate()));
                }
            }

        }
    }
}
