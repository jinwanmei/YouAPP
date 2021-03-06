package com.youappcorp.project.containermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.containermanager.jpa.URLMappingMetaJPARepo;
import com.youappcorp.project.containermanager.model.URLMappingMeta;

@Service(value="internalURLMappingMetaServiceImpl.transation.jpa")
public class InternalURLMappingMetaServiceImpl extends InternalServiceSupport<URLMappingMeta>{

	@Autowired
	private URLMappingMetaJPARepo urlMappingMetaRepo;
	
	@Override
	public JIPersist<?, URLMappingMeta, String> getRepo() {
		return urlMappingMetaRepo;
	}
	
}
