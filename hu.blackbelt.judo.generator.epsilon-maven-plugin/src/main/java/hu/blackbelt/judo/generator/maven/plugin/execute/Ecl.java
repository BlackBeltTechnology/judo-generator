package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Ecl extends Eol {
	
	@Parameter(property = "exportMatchTrace", defaultValue = "matchTrace")
	private String exportMatchTrace;

	@Parameter(property = "useMatchTrace", defaultValue = "matchTrace")
	private String useMatchTrace;

	private EclModule eclModule;

    IEolExecutableModule getModule(Map<Object, Object> context) throws MojoExecutionException {
        eclModule = new EclModule();
        if (useMatchTrace != null) {
			eclModule.getContext().setMatchTrace((MatchTrace)context.get(useMatchTrace));
		}
        
        if (exportMatchTrace != null) {
			context.put(
				exportMatchTrace, 
				eclModule.getContext().getMatchTrace().getReduced());
		}
        return eclModule;
    };
}
