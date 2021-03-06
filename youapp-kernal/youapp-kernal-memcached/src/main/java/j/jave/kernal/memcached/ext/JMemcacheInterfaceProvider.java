package j.jave.kernal.memcached.ext;

import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.service.JServiceInterfaceShared;
import j.jave.kernal.jave.service.JXMLConfigProvider;
import j.jave.kernal.memcached.JDefaultMemcachedDisService;
import j.jave.kernal.memcached.JMemcacheProperties;

@JServiceInterfaceShared
public final class JMemcacheInterfaceProvider extends JXMLConfigProvider {
	
	private static final JMemcacheInterfaceProvider INSTANCE=new JMemcacheInterfaceProvider();
	
	private JMemcacheInterfaceProvider(){}
	
	public static JMemcacheInterfaceProvider get(){
		return INSTANCE;
	}

	@Override
	protected String key() {
		return JMemcacheProperties.DEFAULT_MEMCACHE_SERVICE_INTERFACE;
	}

	@Override
	protected Class<? extends JService> defaultInterface() {
		return JDefaultMemcachedDisService.class;
	}
	
	@Override
	protected String desc() {
		return "Memcache-provider";
	}
	
	
}
