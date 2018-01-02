package hu.blackbelt.judo.generator.maven.plugin.execute;

import java.util.Map;

import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;

public class Eml extends Etl {
	
	@Parameter(property = "useMatchTrace", defaultValue = "matchTrace")
	private String useMatchTrace;
	
	private EmlModule emlModule;

    IEolExecutableModule getModule(Map<Object, Object> context) {
        emlModule = new EmlModule();
        if (useMatchTrace != null) {
			emlModule.getContext().setMatchTrace((MatchTrace)context.get(useMatchTrace));
		}
        return emlModule;
    };
}
