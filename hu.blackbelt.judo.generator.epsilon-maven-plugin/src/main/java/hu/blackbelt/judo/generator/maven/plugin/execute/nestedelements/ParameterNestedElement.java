package hu.blackbelt.judo.generator.maven.plugin.execute.nestedelements;

import java.io.File;

public class ParameterNestedElement {
	
	protected String name;
	protected String value;
	protected File file;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		setValue(file.getAbsolutePath());
	}
	
}
