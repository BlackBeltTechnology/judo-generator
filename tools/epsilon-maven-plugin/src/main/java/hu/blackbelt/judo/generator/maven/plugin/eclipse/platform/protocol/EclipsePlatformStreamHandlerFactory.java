package hu.blackbelt.judo.generator.maven.plugin.eclipse.platform.protocol;

import java.io.File;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Map;

public class EclipsePlatformStreamHandlerFactory implements URLStreamHandlerFactory {

    final Map<String, File> urlMapping;

    public EclipsePlatformStreamHandlerFactory(Map<String, File> urlMapping) {
        this.urlMapping = urlMapping;
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {

        //this will only override the chosen protocol
        if ( protocol.equalsIgnoreCase("platform") )
            return new EclipsePlatformUrlStreamHandler(urlMapping);
        else
            return null;
    }
}
