package hu.blackbelt.judo.generator.maven.plugin.upload;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

public class Artifact {
    @Parameter(name = "file")
    File file;

    @Parameter(name = "type")
    String type;

    @Parameter(name = "classifier")
    String classifier;
}
