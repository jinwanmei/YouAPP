package me.bunny.kernel.mock;

import java.util.Map;

import me.bunny.kernel.jave.service.JService;

public interface JURIGetDataService extends JService{

	public Object getData(String uri,Map<String, Object>... params) throws Exception;
	
}