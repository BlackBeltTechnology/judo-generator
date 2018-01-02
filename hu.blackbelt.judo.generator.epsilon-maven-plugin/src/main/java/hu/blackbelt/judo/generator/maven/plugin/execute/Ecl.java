package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.IEolExecutableModule;

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
        	if (context.get(useMatchTrace) != null) {
        		eclModule.getContext().setMatchTrace((MatchTrace)context.get(useMatchTrace));
        	} else {
        		eclModule.getContext().setMatchTrace(new MatchTrace());
        	}
		}
        
        return eclModule;
    };
    
    @Override
	public void post(Map<Object, Object> context) {
    	 if (exportMatchTrace != null) {
 			context.put(
 				exportMatchTrace, 
 				eclModule.getContext().getMatchTrace().getReduced());
 		}
    }
}
