package hu.blackbelt.judo.generator.maven.plugin.eclipse.platform.protocol;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Map;

public class EclipsePlatformUrlStreamHandler extends URLStreamHandler {

    final Map<String, File> urlMapping;

    public EclipsePlatformUrlStreamHandler(Map<String, File> urlMapping) {
        this.urlMapping = urlMapping;
    }

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new EclipsePlatformUrlConnection(u, urlMapping);
    }
}
