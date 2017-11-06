package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.eol.IEolExecutableModule;

public class Egl extends Eol {
    IEolExecutableModule getModule() {
        return new EglTemplateFactoryModuleAdapter(new EglTemplateFactory());
    };
}
