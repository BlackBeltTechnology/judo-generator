package hu.blackbelt.judo.generator.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Mojo(
    name = "deleteOverridenGeneratedFiles",
    defaultPhase = LifecyclePhase.GENERATE_RESOURCES
)
public class DeleteOverridenGeneratedFilesMojo extends AbstractMojo {

    @Parameter(required = true, defaultValue = "${project.build.directory}/generated-sources/generator-maven-plugin/java")
    private File generatedFilesDirectory;

    @Parameter(required = true, defaultValue = "${project.basedir}/src/main/java")
    private File javaSourceDirectory;

    @Parameter(required = true, defaultValue = "${project.build.outputDirectory}")
    private File compiledClassesDirectory;
    
    public DeleteOverridenGeneratedFilesMojo() {
    }
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Files.walkFileTree(javaSourceDirectory.toPath(), new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    getLog().info("Found: " + file.toString());
                    Path toDeleteInGeneratedDir = generatedFilesDirectory.toPath().resolve(javaSourceDirectory.toPath().relativize(file));
                    getLog().info("To be deleted: " +toDeleteInGeneratedDir.toString());
                    if (toDeleteInGeneratedDir.toFile().exists()) {
                        boolean res = toDeleteInGeneratedDir.toFile().delete();
                        getLog().info(String.format("File [%s] deleted: %b", toDeleteInGeneratedDir.toString(), res));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            Files.walkFileTree(compiledClassesDirectory.toPath(), new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    getLog().info("Found: " + file.toString());
                    Path toDeleteInGeneratedDir = generatedFilesDirectory.toPath().resolve(compiledClassesDirectory.toPath().relativize(file));
                    getLog().info("To be deleted: " +toDeleteInGeneratedDir.toString());
                    toDeleteInGeneratedDir = Paths.get(toDeleteInGeneratedDir.toString().replace(".class", ".java"));
                    if (toDeleteInGeneratedDir.toFile().exists()) {
                        boolean res = toDeleteInGeneratedDir.toFile().delete();
                        getLog().info(String.format("File [%s] deleted: %b", toDeleteInGeneratedDir.toString(), res));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {
            getLog().error("deleteOverridenGeneratedFiles failed! ",  e);
        }
    }

}
