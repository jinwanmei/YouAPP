package com.youappcorp.project.containermanager.model;

import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 */
@Entity
@Table(name="AppMeta")
public class AppMeta extends JJpaBaseModel  {
	
	private String appName;
	
	private String appCompName;
	
	private String appVersion;
	
	private String unique;
	
	private String appDesc;
	
	private String appActive;

	@Column(name="APP_NAME")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name="UNIQUE")
	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	@Column(name="APP_COMP_NAME")
	public String getAppCompName() {
		return appCompName;
	}

	public void setAppCompName(String appCompName) {
		this.appCompName = appCompName;
	}

	@Column(name="APP_VERSION")
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@Column(name="APP_DESC")
	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	@Column(name="APP_ACTIVE")
	public String getAppActive() {
		return appActive;
	}

	public void setAppActive(String appActive) {
		this.appActive = appActive;
	}

	
}