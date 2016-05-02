package com.youappcorp.project.containermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.repo.AppMetaRepo;

@Service(value="internalAppMetaServiceImpl.transation.jpa")
public class InternalAppMetaServiceImpl extends InternalServiceSupport<AppMeta>{

	@Autowired
	private AppMetaRepo appMetaRepo;
	
	@Override
	public JIPersist<?, AppMeta> getRepo() {
		return appMetaRepo;
	}
	
}
