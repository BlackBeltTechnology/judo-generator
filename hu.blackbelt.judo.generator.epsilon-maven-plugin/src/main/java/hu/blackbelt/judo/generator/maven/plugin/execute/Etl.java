package hu.blackbelt.judo.generator.maven.plugin.execute;

import java.util.Map;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.etl.EtlModule;

public class Etl extends Eol {

    IEolExecutableModule getModule(Map<Object, Object> context) {
        return new EtlModule();
    };

}
