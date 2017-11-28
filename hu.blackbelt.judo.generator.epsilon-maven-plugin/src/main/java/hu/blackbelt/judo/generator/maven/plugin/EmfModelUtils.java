package hu.blackbelt.judo.generator.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.CachedResourceSet;
import org.eclipse.epsilon.emc.emf.DefaultXMIResource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

import com.google.common.collect.Maps;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static hu.blackbelt.judo.generator.maven.plugin.EmfModelUtils.load;
import static java.util.stream.Collectors.joining;

public final class EmfModelUtils {

    public static EmfModel load(ResourceSet resourceSet, ModelRepository repository, Model emfModel, File modelFile) throws MojoExecutionException {

        final EmfModel model = createEmfModel(resourceSet);

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
        URI convertFileToUri = convertFileToUri(modelFile);
		properties.put(EmfModel.PROPERTY_MODEL_URI, convertFileToUri);
        System.out.println("Registering MODEL_URI:" + convertFileToUri);

        if (metamodelFile != null) {
            properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, convertFileToUri(metamodelFile));
        }

        if (emfModel.getPlatformAlias() != null && emfModel.getPlatformAlias().trim() != "") {
        	properties.put(EmfModel.PROPERTY_MODEL_URI, emfModel.getPlatformAlias());
            System.out.println("Registering MODEL_URI:" + emfModel.getPlatformAlias());
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

    public static List<EPackage> registerMetamodel(ResourceSet resourceSet, HashMap<String, List<EPackage>> managedMetamodels, String fileName) throws Exception {
        // List<EPackage> ePackages = EmfUtil.register(URI.createPlatformResourceURI(fileName, true), EPackage.Registry.INSTANCE);
        List<EPackage> ePackages = EmfUtil.register(URI.createFileURI(fileName), resourceSet.getPackageRegistry());
        managedMetamodels.put(fileName, ePackages);
        return ePackages;
    }

    public static ResourceSet initResourceSet() {
    	ResourceSet rs = new CachedResourceSet();
    	/*rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("library", new XMIResourceFactoryImpl());
    	rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new XMIResourceFactoryImpl());
    	rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new XMLResourceFactoryImpl()); */
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new DefaultXMIResource.Factory());

		if (rs.getPackageRegistry().getEPackage(EcorePackage.eNS_URI) == null) {
			rs.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		}

    	UMLResourcesUtil.initLocalRegistries(rs);


    	return rs;
    }
    
    public static EmfModel createEmfModel(ResourceSet resourceSet) {
        // return new EmfModel();
    	return new ResourceSetBasedEmfModel(resourceSet);
    }



}
