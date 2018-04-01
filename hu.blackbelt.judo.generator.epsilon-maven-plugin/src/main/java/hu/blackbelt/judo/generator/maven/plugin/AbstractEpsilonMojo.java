package hu.blackbelt.judo.generator.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hu.blackbelt.judo.generator.maven.plugin.EmfModelUtils.load;

public abstract class AbstractEpsilonMojo extends AbstractMojo{

    @Parameter(defaultValue = "${project}", readonly = true)
    public MavenProject project;

    @Component
    public RepositorySystem repoSystem;

    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true, required = true)
    public RepositorySystemSession repoSession;

    @Parameter(defaultValue = "${project.remoteProjectRepositories}", readonly = true, required = true)
    public List<RemoteRepository> repositories;

    @Parameter(readonly = true)
    public List<String> metaModels;

    @Parameter(readonly = true)
    public List<Model> models;
    
    private Log log = new MavenLog(getLog());

    static {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
    }


    protected File getArtifact(String url) throws MojoExecutionException {
        if (url.startsWith("mvn:")) {
            Artifact artifact = new DefaultArtifact(url.toString().substring(4));
            ArtifactRequest req = new ArtifactRequest().setRepositories(this.repositories).setArtifact(artifact);
            ArtifactResult resolutionResult;
            try {
                resolutionResult = this.repoSystem.resolveArtifact(this.repoSession, req);

            } catch (ArtifactResolutionException e) {
                throw new MojoExecutionException("Artifact " + url.toString() + " could not be resolved.", e);
            }

            // The file should exists, but we never know.
            File file = resolutionResult.getArtifact().getFile();
            if (file == null || !file.exists()) {
                log.warn("Artifact " + url.toString() + " has no attached file. Its content will not be copied in the target model directory.");
            }
            return file;
        }
        throw new MojoExecutionException("Artifact " + url.toString() + " could not be resolved.");
    }
    
    public URI getArtifactAsEclipseURI(String url) throws MojoExecutionException {
    	if (url.startsWith("mvn:")) {
            return URI.createFileURI(getArtifact(url).getAbsolutePath());
        } else if (isValidURL(url)) {
        	return URI.createURI(url);
        } else {
            return URI.createFileURI(new File(url).getAbsolutePath());
        }
    }

    public void addMetaModels(ResourceSet resourceSet) throws Exception {
        if (metaModels != null) {
            for (String metaModel : metaModels) {
        		log.info("Registering ecore: " + metaModel);
        		URI uri = getArtifactAsEclipseURI(metaModel);
        		log.info("    Meta model: " + uri);
                List<EPackage> ePackages = EmfModelUtils.register(resourceSet, uri, true);
                log.info("    EPackages: " + ePackages.stream().map(e -> e.getNsURI()).collect(Collectors.joining(", ")));
            }
        }

    }

    public void addModels(ResourceSet resourceSet, ModelRepository modelRepository, Map<Model, EmfModel> emfModels) throws MojoExecutionException {
        if (models != null) {
            for (Model emf : models) {
                log.info("Model: " + emf.toString());
                URI artifactFile = getArtifactAsEclipseURI(emf.getArtifact());
                log.info("    Artifact file: : " + artifactFile.toString());
                emfModels.put(emf, load(log, resourceSet, modelRepository, emf, artifactFile));
            }
        }

    }

    public boolean isValidURL(String url) {

        URL u = null;

        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }

        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }


}
