package hu.blackbelt.judo.generator.maven.plugin.execute;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;

import java.util.Collection;

public class Evl extends Eol {
    private EvlModule module = new EvlModule();

    IEolExecutableModule getModule() {
        return module;
    };

    public boolean isOk() {
        return module.getContext().getUnsatisfiedConstraints().isEmpty();
    }

    public String toString() {
        Collection<UnsatisfiedConstraint> unsatisfied = module.getContext().getUnsatisfiedConstraints();

        StringBuffer stringBuffer = new StringBuffer();

        if (unsatisfied.size() > 0) {
            stringBuffer.append(unsatisfied.size() + " constraint(s) have not been satisfied\n");
            for (UnsatisfiedConstraint uc : unsatisfied) {
                stringBuffer.append(uc.getMessage() + "\n");
            }
        }
        else {
            stringBuffer.append("All constraints have been satisfied");
        }
        return stringBuffer.toString();

    }
}
