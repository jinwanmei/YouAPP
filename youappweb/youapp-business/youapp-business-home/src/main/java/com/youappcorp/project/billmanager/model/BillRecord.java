package com.youappcorp.project.billmanager.model;

import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.platform.data.web.model.JInputModel;
import j.jave.platform.data.web.model.JOutputModel;

public class BillRecord extends Bill implements JOutputModel , JInputModel {

	private String userName;
	
	private String goodTypeName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGoodTypeName() {
		return goodTypeName;
	}

	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
	}
	
	public Bill toBill(){
		return JObjectUtils.simpleCopy(this, Bill.class);
	}
	
}
