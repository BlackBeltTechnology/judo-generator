package hu.blackbelt.judo.generator.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.ModelRepository;

import java.io.File;

import static java.util.stream.Collectors.joining;

public final class EmfModelLoader {

    public static EmfModel load(ModelRepository repository, Model emfModel, File modelFile) throws MojoExecutionException {

        final EmfModel model = createEmfModel();

        final StringProperties properties = new StringProperties();
        properties.put(EmfModel.PROPERTY_NAME, emfModel.getName() + "");
        if (emfModel.getAliases() != null) {
            properties.put(EmfModel.PROPERTY_ALIASES, emfModel.getAliases().stream().collect(joining(",")) + "");
        } else {
            properties.put(EmfModel.PROPERTY_ALIASES, "");
        }
        properties.put(EmfModel.PROPERTY_READONLOAD, emfModel.isReadOnLoad()+ "");
        properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, emfModel.isStoreOnDisposal() + "");
        properties.put(EmfModel.PROPERTY_EXPAND, emfModel.isExpand() + "");
        properties.put(EmfModel.PROPERTY_CACHED, emfModel.isCached() + "");
        properties.put(EmfModel.PROPERTY_REUSE_UNMODIFIED_FILE_BASED_METAMODELS, emfModel.isReuseUnmodifiedFileBasedMetamodels() + "");

        String metamodelUri = emfModel.getMetaModelUris().stream().collect(joining(","));
        //File modelFile = emfModel.getModelFile();
        String modelUri = emfModel.getMetaModelUris().stream().collect(joining(","));
        File metamodelFile = emfModel.getMetaModelFile();

        if (metamodelUri != null) {
            properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelUri + "");
        }

        /*
        if (modelFile != null && modelUri != null) {
            throw new MojoExecutionException("Only one of modelFile or modelUri may be used");
        } else if (modelUri != null) {
            properties.put(EmfModel.PROPERTY_MODEL_URI, modelUri);
        } else {
            properties.put(EmfModel.PROPERTY_MODEL_URI, convertFileToUri(modelFile));
        }
        */
        properties.put(EmfModel.PROPERTY_MODEL_URI, convertFileToUri(modelFile));

        if (metamodelFile != null) {
            properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, convertFileToUri(metamodelFile));
        }

        if (emfModel.getPlatformAlias() != null && emfModel.getPlatformAlias().trim() != "") {
            URIConverter.URI_MAP.put(URI.createURI(emfModel.getPlatformAlias()), URI.createFileURI(modelFile.getAbsolutePath()));
        }

        try {
            model.load(properties);
            repository.addModel(model);
            return model;
        } catch (EolModelLoadingException e) {
            throw new MojoExecutionException("Cannot load model: " + modelFile.toString(), e);
        }
    }

    private static URI convertFileToUri(File file) {
        return file == null ? null : URI.createFileURI(file.getAbsolutePath());
    }

    // This logic has been extracted so that it can be stubbed out in tests
    protected static EmfModel createEmfModel() {
        return new EmfModel();
    }

}
