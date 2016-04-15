/**
 * 
 */
package com.youappcorp.project.resource.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;

/**
 * @author J
 */
@JTable(name="RESOURCES")
public class Resource extends JBaseModel {

	@JColumn(name="URL",type=JSQLType.VARCHAR,length=128)
	private String url;
	
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;

	
	private ResourceExtend resourceExtend;

	public ResourceExtend getResourceExtend() {
		return resourceExtend;
	}


	public void setResourceExtend(ResourceExtend resourceExtend) {
		this.resourceExtend = resourceExtend;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
}