package j.jave.web.htmlclient;

import j.jave.kernal.jave.model.JModel;

public class ModuleState implements JModel {

	private String jarUrl;
	
	private boolean active;

	public String getJarUrl() {
		return jarUrl;
	}

	public void setJarUrl(String jarUrl) {
		this.jarUrl = jarUrl;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
