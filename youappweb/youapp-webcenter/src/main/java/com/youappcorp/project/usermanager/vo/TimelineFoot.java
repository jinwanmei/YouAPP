package com.youappcorp.project.usermanager.vo;

import me.bunny.kernel._c.model.JModel;

public class TimelineFoot implements JModel {

	private String viewAction;
	
	private String deleteAction;

	public String getViewAction() {
		return viewAction;
	}

	public void setViewAction(String viewAction) {
		this.viewAction = viewAction;
	}

	public String getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}
	
	
	
}
