package hu.blackbelt.judo.generator.maven.plugin.execute.nestedelements;

import org.apache.maven.plugin.MojoExecutionException;
import org.eclipse.epsilon.egl.formatter.Formatter;

public class EglDefaultFormatterNestedElement {

	private Class<? extends Formatter> implementation;

	public Class<? extends Formatter> getImplementation() {
		return implementation;
	}

	public void setImplementation(Class<? extends Formatter> implementation) throws MojoExecutionException {
		if (Formatter.class.isAssignableFrom(implementation)) {
			this.implementation = implementation;
			
		} else {
			throw new MojoExecutionException("The implementation parameter must be a class that subtypes org.eclipse.epsilon.egl.formatter.Formatter.");
		}
	}
}
