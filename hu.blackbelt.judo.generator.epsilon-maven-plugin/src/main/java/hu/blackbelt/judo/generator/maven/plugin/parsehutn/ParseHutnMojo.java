package hu.blackbelt.judo.generator.maven.plugin.parsehutn;

import hu.blackbelt.judo.generator.maven.plugin.AbstractEpsilonMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.hutn.HutnContext;
import org.eclipse.epsilon.hutn.HutnModule;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Mojo(
    name = "parseHutn",
    defaultPhase = LifecyclePhase.GENERATE_RESOURCES
)
public class ParseHutnMojo extends AbstractEpsilonMojo {
    @Parameter(defaultValue = "", readonly = true, required = true)
    private File hutnFile;

    @Parameter(defaultValue = "", readonly = true, required = true)
    private File targetFile;

    public ParseHutnMojo() {
    }

    // This logic has been extracted so that it can be stubbed out in tests
    protected EmfModel createEmfModel() {
        return new EmfModel();
    }

    synchronized public void execute() throws MojoExecutionException, MojoFailureException {
        // EclipsePlatformStreamHandlerFactory.urlMapping.clear();
        try {

            Exception ex = null;
            try {
                addMetaModels();
                addModels();

                getLog().info("URL converters: " + URIConverter.URI_MAP.entrySet().stream().map(e -> e.getKey() + "->" + e.getValue()).collect(Collectors.joining(", ")));

                HutnModule module = new HutnModule();
                setHutnContext(module);
                parseHutnAndStoreModel(module);

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
           // EclipsePlatformStreamHandlerFactory.urlMapping.clear();
        }
    }


    private void parseHutnAndStoreModel(HutnModule module)
            throws Exception {
        getLog().info("Start parsing HUTN file");

        if (module.parse(hutnFile)) {
            getLog().info("Parsing successfull, storing transformed emf model");
            List<File> files = module.storeEmfModel(targetFile.getParentFile(), targetFile.getName(), "any");
            for (File file : files) {
                getLog().info("Transformed: " + file);
            }
        } else {
            throw new MojoExecutionException("Could parse HUTN");
        }
    }

    private void setHutnContext(HutnModule module) {
        HutnContext context = new HutnContext(module);
        context.setErrorStream(System.err);
        context.setWarningStream(System.out);
        context.setOutputStream(System.out);
        context.setModelRepository(modelRepository);
        module.setContext(context);
    }


}
