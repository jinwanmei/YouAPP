package j.jave.platform.basicsupportcomp.core.container;

public enum Scheme {

	BEAN("bean"),CONTROLLER("controller");
	
	private final String value;
	
	private Scheme(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
