package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Eol {
    @Parameter(name = "source", required = true)
    File source;

    @Parameter(name = "parameters")
    List<EolProgramParameter> parameters;

    
    private EolModule module = new EolModule();

    IEolExecutableModule getModule(Map<Object, Object> context) throws MojoExecutionException {
        return module;
    };

    public boolean isOk() {
        return true;
    }

    public String toString() {
        return "";
    }

}
