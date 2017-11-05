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
        }
    }
}
