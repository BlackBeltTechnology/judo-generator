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
        String urlStr = url.toExternalForm().replace("platform: ", "platform:");
        System.out.println("Open platform URL: " + urlStr);

        if (!urlMapping.containsKey(urlStr)) {
            System.err.println("URL is not mapped: " + urlStr);
            throw new IOException("URL not mapped: " + urlStr);
        } else {
            realFile = urlMapping.get(urlStr);
        }
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        System.out.println("Open file stream: " + realFile.getAbsolutePath());
        return new FileInputStream(realFile);
    }
}
