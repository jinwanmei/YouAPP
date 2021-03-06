package com.youappcorp.project.containermanager.vo;

import j.jave.platform.data.web.model.BaseCriteria;

public class AppMetaCriteria extends BaseCriteria {
	
	private String appName;
	
	private String appCompName;
	
	private String appVersion;
	
	private String appUnique;
	
	private String appDesc;
	
	private String appActive;
	
	private String appHost;
	
	/**
	 * the friendly url that is the same functionality as {@link #unique()}
	 */
	private String friendlyUrl;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCompName() {
		return appCompName;
	}

	public void setAppCompName(String appCompName) {
		this.appCompName = appCompName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppUnique() {
		return appUnique;
	}

	public void setAppUnique(String appUnique) {
		this.appUnique = appUnique;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppActive() {
		return appActive;
	}

	public void setAppActive(String appActive) {
		this.appActive = appActive;
	}

	public String getAppHost() {
		return appHost;
	}

	public void setAppHost(String appHost) {
		this.appHost = appHost;
	}

	public String getFriendlyUrl() {
		return friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}
	
}
