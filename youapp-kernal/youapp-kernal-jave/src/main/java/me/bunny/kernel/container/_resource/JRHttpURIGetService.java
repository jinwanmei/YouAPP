package me.bunny.kernel.container._resource;

import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JScheme;

public class JRHttpURIGetService implements JResourceURIGetService{

	@Override
	public String getGetRequestURI(String unique, String path) {
		return JExecutableURIUtil.getGetRequestURI(unique,path,JScheme.REMOTE_HTTP);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return JExecutableURIUtil.getPutRequestURI(unique,path,JScheme.REMOTE_HTTP);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getDeleteRequestURI(unique,path,JScheme.REMOTE_HTTP);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExistRequestURI(unique,path,JScheme.REMOTE_HTTP);
	}

	@Override
	public String getExecuteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExecuteRequestURI(unique,path,JScheme.REMOTE_HTTP);
	}

	
}