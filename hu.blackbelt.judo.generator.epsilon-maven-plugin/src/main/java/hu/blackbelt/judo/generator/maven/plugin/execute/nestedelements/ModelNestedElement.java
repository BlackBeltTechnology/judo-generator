package hu.blackbelt.judo.generator.maven.plugin.execute.nestedelements;

public class ModelNestedElement {
	
	protected String ref;
	protected String as;
	protected String alias;
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String name) {
		this.ref = name;
	}

	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}
	
}
