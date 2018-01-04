package hu.blackbelt.judo.generator.maven.plugin;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;

import java.util.HashMap;

public class OptimizedXmiResourceImpl extends XMIResourceImpl {

	
	protected boolean useXmiIds = super.useUUIDs();
	
	public OptimizedXmiResourceImpl() {
		super();
		setOptimizedOptions();
	}

	public OptimizedXmiResourceImpl(URI uri) {
		super(uri);
		setOptimizedOptions();
	}
	
	/*
	 * TODO: Parameters have to delegate to maven
	 */
	private void setOptimizedOptions() {
		super.setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
		this.getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);	
		this.getDefaultLoadOptions().put(XMIResource.OPTION_USE_DEPRECATED_METHODS, false);
		this.getDefaultLoadOptions().put(XMIResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl(10000, true));
		this.getDefaultLoadOptions().put(XMIResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<Object,Object>());
		this.getDefaultLoadOptions().put(XMIResource.OPTION_DISABLE_NOTIFY, Boolean.TRUE);
		this.getDefaultLoadOptions().put(XMIResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);

	}

	@Override
	protected boolean useUUIDs() {
		return useXmiIds;
	}
	
	public void setUseXmiIds(boolean useXmiIds) {
		this.useXmiIds = useXmiIds;
	}
	
	public boolean isUseXmiIds() {
		return useXmiIds;
	}
	
	public static class Factory implements Resource.Factory {

		@Override
		public Resource createResource(URI uri) {
			return new OptimizedXmiResourceImpl(uri);
		}
		
	}
	

}

