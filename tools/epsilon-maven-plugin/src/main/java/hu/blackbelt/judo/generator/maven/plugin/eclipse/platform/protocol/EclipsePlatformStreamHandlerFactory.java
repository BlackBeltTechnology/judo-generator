package hu.blackbelt.judo.generator.maven.plugin.eclipse.platform.protocol;

import java.io.File;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EclipsePlatformStreamHandlerFactory implements URLStreamHandlerFactory {


    public final static Map<String, File> urlMapping = new ConcurrentHashMap<>();

    static {
        URL.setURLStreamHandlerFactory(new EclipsePlatformStreamHandlerFactory());
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
