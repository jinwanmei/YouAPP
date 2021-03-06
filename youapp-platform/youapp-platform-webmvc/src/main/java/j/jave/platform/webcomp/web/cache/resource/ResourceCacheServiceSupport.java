package j.jave.platform.webcomp.web.cache.resource;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.service.JCacheService;
import j.jave.kernal.jave.support.resourceuri.DefaultIdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.IdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.InitialResource;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheRefreshEvent;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheService;
import j.jave.platform.sps.support.ehcache.subhub.EhcacheDelegateService;
import j.jave.platform.webcomp.WebCompProperties;

import java.util.HashSet;
import java.util.Set;

public abstract class ResourceCacheServiceSupport<T,M> implements ResourceCacheService<T>,
JSingleStaticMemoryCacheIO<M>,InitialResource{

	private JCacheService cacheService=null;
	{
		String cacheImpl=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_RESOURCE_CACHE_SERVICE_INTERFACE, EhcacheDelegateService.class.getName());
		cacheService=JServiceHubDelegate.get().getService(this, JClassUtils.load(cacheImpl));
	}
	private static DefaultIdentifierGenerator defaultIdentifierGenerator=new DefaultIdentifierGenerator();
	
	private static Set<InitialResource> initialResources=new HashSet<InitialResource>();
	
	static Set<InitialResource> getInitialResources() {
		return initialResources;
	}
	
	public ResourceCacheServiceSupport() {
		initialResources.add(this);
	}
	
	@Override
	public IdentifierGenerator generator() {
		return defaultIdentifierGenerator;
	}

	@Override
	public T set(String key, T object) {
		return (T) cacheService.putNeverExpired(generator().key(key), object);
	}

	@Override
	public T get(String key) {
		return (T) cacheService.get(generator().key(key));
	}
	
	@Override
	public final M set() {
		initResource(JConfiguration.get());
		return null;
	}

	@Override
	public T remove(String key) {
		return (T) cacheService.remove(generator().key(key));
	}
	
	@Override
	public final M get() {
		throw new JOperationNotSupportedException("not supported.");
	}
	
	@Override
	public Object trigger(ResourceCacheRefreshEvent event) {
		initResource(JConfiguration.get());
		return true;
	}
}
