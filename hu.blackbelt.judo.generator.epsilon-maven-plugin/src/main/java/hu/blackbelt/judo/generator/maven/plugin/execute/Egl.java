package hu.blackbelt.judo.generator.maven.plugin.execute;

import java.io.File;
import java.net.URI;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.eol.IEolExecutableModule;

public class Egl extends Eol {

	public static final String ARTIFACT_ROOT = "ARTIFACT_ROOT";

	private EglTemplateFactoryModuleAdapter module;

	@Parameter(property = "outputRoot", defaultValue = "${project.basedir}/target/generated-sources")
	private String outputRoot;

	IEolExecutableModule getModule(Map<Object, Object> context) throws MojoExecutionException {
		EglTemplateFactory templateFactory;
		try {
			templateFactory = EglFileGeneratingTemplateFactory.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("Could not isntattiate tempalte factory", e1);
		}

		File outputRootDir = new File(outputRoot);
		if (!outputRootDir.exists()) {
			outputRootDir.mkdirs();
		}

		if (templateFactory instanceof EglFileGeneratingTemplateFactory && outputRoot != null) {
			try {
				((EglFileGeneratingTemplateFactory) templateFactory).setOutputRoot(outputRootDir.getAbsolutePath());
				if (context.get(ARTIFACT_ROOT)!= null) {
					URI main = (URI)context.get(ARTIFACT_ROOT); 
					((EglFileGeneratingTemplateFactory) templateFactory).setRoot(main);
				} else {
					throw new MojoExecutionException("Artifact must be set!");
				}
			} catch (EglRuntimeException e) {
				throw new MojoExecutionException("Could not create tempalte factory", e);
			}
		}

		module = new EglTemplateFactoryModuleAdapter(templateFactory);
		return module;
	}

	public String getOutputRoot() {
		return outputRoot;
	}

	public void setOutputRoot(String outputRoot) {
		this.outputRoot = outputRoot;
	}

}
