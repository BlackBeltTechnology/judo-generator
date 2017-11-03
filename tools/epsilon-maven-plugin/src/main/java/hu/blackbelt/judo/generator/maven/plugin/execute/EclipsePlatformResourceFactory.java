package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;

import java.io.File;
import java.util.Map;

public class EclipsePlatformResourceFactory implements Resource.Factory {

    final Map<String, File> urlMapping;

    public EclipsePlatformResourceFactory(Map<String, File> urlMapping) {
        this.urlMapping = urlMapping;
    }

    @Override
    public Resource createResource(URI uri) {
        return new BinaryResourceImpl();
    }
}
