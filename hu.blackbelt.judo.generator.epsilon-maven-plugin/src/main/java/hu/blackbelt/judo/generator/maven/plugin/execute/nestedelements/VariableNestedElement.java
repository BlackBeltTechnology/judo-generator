package hu.blackbelt.judo.generator.maven.plugin.execute.nestedelements;

public class VariableNestedElement {
	
	protected String ref;
	protected String as;
	protected boolean optional;
	protected boolean ant;
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getAs() {
		return as;
	}
	
	public void setAs(String as) {
		this.as = as;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}
}
