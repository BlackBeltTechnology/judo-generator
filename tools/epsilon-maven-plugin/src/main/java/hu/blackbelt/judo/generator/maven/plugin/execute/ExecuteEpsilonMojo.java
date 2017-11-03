package hu.blackbelt.judo.generator.maven.plugin.execute;

import com.google.common.collect.Maps;
import hu.blackbelt.judo.generator.maven.plugin.AbstractEpsilonMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.models.IModel;

@Mojo(
    name = "execute",
    defaultPhase = LifecyclePhase.GENERATE_RESOURCES
)
public class ExecuteEpsilonMojo extends AbstractEpsilonMojo {


    public ExecuteEpsilonMojo() {
    }

    // This logic has been extracted so that it can be stubbed out in tests
    protected EmfModel createEmfModel() {
        return new EmfModel();
    }

    synchronized public void execute() throws MojoExecutionException, MojoFailureException {
        // Map<String, File> urlMapping = Maps.newConcurrentMap();
        // EclipsePlatformStreamHandlerFactory.urlMapping.clear();
        emfModels = Maps.newConcurrentMap();
        try {

            Exception ex = null;
            try {
                addMetaModels();
                addModels();
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

    /*
    public void registerPlatformResourceFactory(Map<String, File> urlMapping) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        reg.getProtocolToFactoryMap("platform").put(new EclipsePlatformResourceFactory(urlMapping));
    } */

    /*
    public void registerResource() {
        Resource.Factory.Registry reg= Resource.Factory.Registry.INSTANCE;
        Map m = reg.getExtensionToFactoryMap();
        m.put("model", new XMIResourceFactoryImpl());
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.getResource(URI.createURI
                        ("C:/eclipse/mydata/runtime-EclipseApplication/librarytest/my.library"),
                true);
    }*/
}
