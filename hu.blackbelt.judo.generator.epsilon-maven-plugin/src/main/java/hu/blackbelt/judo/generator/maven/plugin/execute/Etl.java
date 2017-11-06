package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.etl.EtlModule;

public class Etl extends Eol {

    IEolExecutableModule getModule() {
        return new EtlModule();
    };

}
