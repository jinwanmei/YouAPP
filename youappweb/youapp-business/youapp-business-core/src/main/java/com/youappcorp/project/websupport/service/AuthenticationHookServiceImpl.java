package com.youappcorp.project.websupport.service;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.access.subhub.AuthenticationHookService;
import j.jave.platform.basicwebcomp.core.service.SessionUser;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.model.UserTracker;
import com.youappcorp.project.usermanager.service.UserService;
import com.youappcorp.project.usermanager.service.UserTrackerService;

@Service(value="com.youappcorp.project.websupport.service.AuthenticationHookServiceImpl")
public class AuthenticationHookServiceImpl implements AuthenticationHookService {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(AuthenticationHookServiceImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	@Override
	public void doAfterLogin(HttpContext httpContext) {
		try{
			UserTracker userTracker=new UserTracker();
			SessionUser sessionUser=httpContext.getUser();
			userTracker.setUserId(sessionUser.getUserId());
			userTracker.setUserName(sessionUser.getUserName());
			userTracker.setIp(httpContext.getIP());
			userTracker.setLoginClient(httpContext.getClient());
			userTrackerService.saveUserTracker(httpContext.getServiceContext(), userTracker);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void doAfterLoginout(HttpContext httpContext) {
		LOGGER.info("loginout..... ----> "+httpContext.getTicket());
	}
}
