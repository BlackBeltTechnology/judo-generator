package hu.blackbelt.judo.generator.maven.plugin.execute;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;

public class Evl extends Eol {
	private EvlModule module = new EvlModule();

	IEolExecutableModule getModule(Map<Object, Object> context) {
		return module;
	};

	public boolean isOk() {
		return unstatisfiedErrors().isEmpty();
	}

	private List<UnsatisfiedConstraint> unstatisfiedWarnings() {
		return module.getContext().getUnsatisfiedConstraints().stream()
				.filter((uc) -> uc.getConstraint().isCritique())
				.collect(Collectors.toList());
	}
	
	private List<UnsatisfiedConstraint> unstatisfiedErrors() {
		return module.getContext().getUnsatisfiedConstraints().stream()
				.filter((uc) -> !uc.getConstraint().isCritique())
				.collect(Collectors.toList());
	}

	public String toString() {
		Collection<UnsatisfiedConstraint> unsatisfied = module.getContext().getUnsatisfiedConstraints();

		StringBuffer stringBuffer = new StringBuffer();

		if (unsatisfied.size() > 0) {
			printErrors(stringBuffer);
			printWarnings(stringBuffer);
		} else {
			stringBuffer.append("All constraints have been satisfied");
		}
		return stringBuffer.toString();

	}

	private void printErrors(StringBuffer stringBuffer) {
		stringBuffer.append(unstatisfiedErrors().size() + " error(s) \n");
		for (UnsatisfiedConstraint uc : unstatisfiedErrors()) {
			stringBuffer.append(uc.getMessage() + "\n");
		}
	}
	
	private void printWarnings(StringBuffer stringBuffer) {
		stringBuffer.append(unstatisfiedWarnings().size() + " warning(s) \n");
		for (UnsatisfiedConstraint uc : unstatisfiedWarnings()) {
			stringBuffer.append(uc.getMessage() + "\n");
		}
	}
}
