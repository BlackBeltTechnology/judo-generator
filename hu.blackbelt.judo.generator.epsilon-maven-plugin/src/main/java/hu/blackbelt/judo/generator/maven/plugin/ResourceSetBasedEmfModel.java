package hu.blackbelt.judo.generator.maven.plugin;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceSetBasedEmfModel extends EmfModel {
	
	private final ResourceSet resourceSet;
	
	public ResourceSetBasedEmfModel(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}
	
	private List<EPackage> attemptFileBasedMetamodelReuse(URI uri) {
		if (!reuseUnmodifiedFileBasedMetamodels || !uri.isFile()) {
			// Reuse has been disabled, or the URI is not for a file: do nothing
			return null;
		}

		final String path = uri.toFileString();
		final File metamodelFile = new File(path);
		final Long lastTimestamp = fileBasedMetamodelTimestamps.get(path);
		if (lastTimestamp == null || metamodelFile.lastModified() != lastTimestamp) {
			// We don't have this URI in our cache yet, or the file
			// has been modified since the last time we read it
			return null;
		}

		return fileBasedMetamodels.get(path);
	}

	private void saveFileBasedMetamodelForReuse(URI uri, List<EPackage> packages) {
		// We always save the previously loaded metamodels, as we might want to force
		// a reload first in one EmfModel and then reuse the metamodel in the next
		// EmfModel.
		if (!uri.isFile()) {
			// The URI is not for a file: do nothing
			return;
		}

		final String path = uri.toFileString();
		final File metamodelFile = new File(path);
		final Long timestamp = metamodelFile.lastModified();
		fileBasedMetamodels.put(path, packages);
		fileBasedMetamodelTimestamps.put(path, timestamp);
	}

	
	protected void determinePackagesFrom(ResourceSet resourceSet) throws EolModelLoadingException {
		packages = new ArrayList<EPackage>();

		for (URI metamodelFileUri : this.metamodelFileUris) {
			List<EPackage> metamodelPackages = null;
			try {
				metamodelPackages = attemptFileBasedMetamodelReuse(metamodelFileUri);
				if (metamodelPackages == null) {
					metamodelPackages = EmfUtil.register(metamodelFileUri, resourceSet.getPackageRegistry(), false);
					saveFileBasedMetamodelForReuse(metamodelFileUri, metamodelPackages);
				}
			} catch (Exception e) {
				throw new EolModelLoadingException(e,this);
			}
			for (EPackage metamodelPackage : metamodelPackages) {
				packages.add(metamodelPackage);
				EmfUtil.collectDependencies(metamodelPackage, packages);
			}
		}
	
		for (URI metamodelUri : this.metamodelUris) {
			EPackage ePackage = resourceSet.getPackageRegistry().getEPackage(metamodelUri.toString());
			if (ePackage == null) {
				throw new EolModelLoadingException(new IllegalArgumentException("Could not locate a metamodel with the URI '" + metamodelUri + "'. Please ensure that this metamodel has been registered with Epsilon."), this);
			}
			packages.add(ePackage);
			EmfUtil.collectDependencies(ePackage, packages);
		}
	}

	@Override
	public void loadModelFromUri() throws EolModelLoadingException {
		determinePackagesFrom(resourceSet);
		
		// Note that AbstractEmfModel#getPackageRegistry() is not usable yet, as modelImpl is not set
		for (EPackage ep : packages) {
			String nsUri = ep.getNsURI();
			if (nsUri == null || nsUri.trim().length() == 0) {
				nsUri = ep.getName();
			}
			resourceSet.getPackageRegistry().put(nsUri, ep);
		}

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
