package hu.blackbelt.judo.generator.maven.plugin;

import com.google.common.collect.Maps;
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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
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


    public HashMap<String, List<EPackage>> managedMetamodels = new HashMap<String, List<EPackage>>();


    public File getArtifact(String name) throws MojoExecutionException {
        if (name.startsWith("mvn:")) {
            Artifact artifact = new DefaultArtifact(name.substring(4));
            ArtifactRequest req = new ArtifactRequest().setRepositories(this.repositories).setArtifact(artifact);
            ArtifactResult resolutionResult;
            try {
                resolutionResult = this.repoSystem.resolveArtifact(this.repoSession, req);

            } catch (ArtifactResolutionException e) {
                throw new MojoExecutionException("Artifact " + name + " could not be resolved.", e);
            }

            // The file should exists, but we never know.
            File file = resolutionResult.getArtifact().getFile();
            if (file == null || !file.exists()) {
                getLog().warn("Artifact " + name + " has no attached file. Its content will not be copied in the target model directory.");
            }
            return file;
        } else if (isValidURL(name)) {
            throw new MojoExecutionException("STandard URL is not supported yet. Only mvn: or file");
        } else {
            /*
            File file = new File(name);
            if (file.exists()) {
                return file;
            } else {
                throw new MojoExecutionException("artifact reference have to be mvn: artifact or existing file.");
            }
            */
            return new File(name);
        }
    }

    public void addMetaModels(ResourceSet resourceSet) throws Exception {
        if (metaModels != null) {
            for (String metaModel : metaModels) {
                getLog().info("Registering ecore: " + metaModel);
                File metaModelFile = getArtifact(metaModel);
                getLog().info("    Meta model: " + metaModelFile.getAbsolutePath());
                List<EPackage> ePackages = EmfModelUtils.registerMetamodel(resourceSet, managedMetamodels, metaModelFile.getAbsolutePath());
                getLog().info("    EPackages: " + ePackages.stream().map(e -> e.getNsURI()).collect(Collectors.joining(", ")));
            }
        }

    }

    public void addModels(ResourceSet resourceSet, ModelRepository modelRepository, Map<Model, EmfModel> emfModels) throws MojoExecutionException {
        emfModels = Maps.newConcurrentMap();
        if (models != null) {
            for (Model emf : models) {
                getLog().info("Model: " + emf.toString());
                File artifactFile = getArtifact(emf.getArtifact());
                getLog().info("    Artifact file: : " + artifactFile.toString());
                emfModels.put(emf, load(resourceSet, modelRepository, emf, artifactFile));

                /*if (emf.getPlatformAlias() != null && emf.getPlatformAlias().trim() != "") {
                    EclipsePlatformStreamHandlerFactory.urlMapping.put(emf.getPlatformAlias().trim(), artifactFile);
                }*/
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
