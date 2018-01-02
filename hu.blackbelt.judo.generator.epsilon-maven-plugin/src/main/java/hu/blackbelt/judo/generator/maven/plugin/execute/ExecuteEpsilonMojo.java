package hu.blackbelt.judo.generator.maven.plugin.execute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import hu.blackbelt.judo.generator.maven.plugin.AbstractEpsilonMojo;
import hu.blackbelt.judo.generator.maven.plugin.EmfModelUtils;
import hu.blackbelt.judo.generator.maven.plugin.Model;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;

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

    public ExecuteEpsilonMojo() {
    }
    synchronized public void execute() throws MojoExecutionException, MojoFailureException {
        Map<Model, EmfModel> emfModels = Maps.newConcurrentMap();
        ResourceSet resourceSet = EmfModelUtils.initResourceSet();
        ModelRepository modelRepository = new ModelRepository();
        Map<Object, Object> context = new HashMap();
        
        try {
            Exception ex = null;
            try {
                addMetaModels(resourceSet);
                addModels(resourceSet, modelRepository, emfModels);
                
                getLog().info("URL converters: \n\t" + URIConverter.URI_MAP.entrySet().stream().map(e -> e.getKey() + "->" + e.getValue()).collect(Collectors.joining("\n\t")));

                if (eolPrograms != null) {
                    for (Eol eolProgram : eolPrograms) {
                        IEolExecutableModule eolModule = eolProgram.getModule(context);
                        eolModule.getContext().setModelRepository(modelRepository);
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
                    for (IModel model : modelRepository.getModels()) {
                        model.setStoredOnDisposal(false);
                    }
                }
                modelRepository.dispose();
                if (ex != null) {
                    throw new MojoExecutionException("Could not run", ex);
                }
            }
        } finally {
        }
    }

    private void executeModule(IEolExecutableModule eolModule, File source, List<Variable> parameters) throws Exception {
        eolModule.parse(source);

        if (eolModule.getParseProblems().size() > 0) {
            getLog().error("Parse errors occured...");
            for (ParseProblem problem : eolModule.getParseProblems()) {
                getLog().error(problem.toString());
            }
            throw new MojoExecutionException("Parse error");
        }
        for (Variable parameter : parameters) {
            eolModule.getContext().getFrameStack().put(parameter);
        }

        Object result = eolModule.execute();
        // getLog().info("Eol execute result: " + result.toString());
    }
}
