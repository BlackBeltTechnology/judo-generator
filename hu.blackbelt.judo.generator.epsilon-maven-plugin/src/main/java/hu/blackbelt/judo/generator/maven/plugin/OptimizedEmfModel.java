package hu.blackbelt.judo.generator.maven.plugin;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.emc.emf.CachedResourceSet;
import org.eclipse.epsilon.emc.emf.DefaultXMIResource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptimizedEmfModel extends EmfModel {
	
	protected ResourceSet createResourceSet() {
		CachedResourceSet ret =  new CachedResourceSet();
		ret.setURIResourceMap(new HashMap<>());
		return ret;
	}

	
	public void loadModelFromUri() throws EolModelLoadingException {
		ResourceSet resourceSet = createResourceSet();
		
		// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new DefaultXMIResource.Factory());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new OptimizedXmiResourceImpl.Factory());

        // Check if global package registry contains the EcorePackage
		if (EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) == null) {
			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		}
		
		super.determinePackagesFrom(resourceSet);
		
		// Note that AbstractEmfModel#getPackageRegistry() is not usable yet, as modelImpl is not set
		for (EPackage ep : packages) {
			String nsUri = ep.getNsURI();
			if (nsUri == null || nsUri.trim().length() == 0) {
				nsUri = ep.getName();
			}
			resourceSet.getPackageRegistry().put(nsUri, ep);
		}
		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		
		Resource model = resourceSet.createResource(modelUri);
		if (this.readOnLoad) {
			try {
				model.load(null);
				if (expand) {
					EcoreUtil.resolveAll(model);
				}
			} catch (IOException e) {
				throw new EolModelLoadingException(e, this);
			}
		}
		modelImpl = model;
	}

}
