package hu.blackbelt.judo.generator.maven.plugin.eclipse.platform.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class EclipsePlatformUrlConnection extends URLConnection {

    final File realFile;

    protected EclipsePlatformUrlConnection(URL url, Map<String, File> urlMapping) throws IOException {
        super(url);
        if (!urlMapping.containsKey(url.toString())) {
            throw new IOException("URL not mapped: " + url.toString());
        } else {
            realFile = urlMapping.get(url.toString());
        }
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(realFile);
    }
}
