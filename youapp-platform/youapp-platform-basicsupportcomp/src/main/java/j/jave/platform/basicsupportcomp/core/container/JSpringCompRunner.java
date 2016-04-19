package j.jave.platform.basicsupportcomp.core.container;

import java.net.URI;

import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;

public class JSpringCompRunner implements JRunner {

	private String unique;
	
	private String name;
	
	private final SpringDynamicJARApplicationCotext dynamicJARApplicationCotext;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	private MicroContainerConfig containerConfig;
	
	public JSpringCompRunner(SpringDynamicJARApplicationCotext dynamicJARApplicationCotext) {
		this.dynamicJARApplicationCotext=dynamicJARApplicationCotext;
		this.componentVersionApplication=dynamicJARApplicationCotext.getComponentVersionApplication();
	}
	
	@Override
	public String unique() {
		return unique;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public boolean accept(URI uri) {
		return Scheme.BEAN.getValue().equals(uri.getScheme());
	}

	@Override
	public Object execute(URI uri,Object object) {
		String beanName=uri.getSchemeSpecificPart();
		return dynamicJARApplicationCotext.getBean(beanName);
	}
	
	@Override
	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.containerConfig=containerConfig;
	}
	
}
