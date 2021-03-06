package j.jave.platform.sps.support.memcached.subhub;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.memcached.JDefaultMemcachedDisService;
import j.jave.kernal.memcached.event.JMemcachedDisAddEvent;
import j.jave.kernal.memcached.event.JMemcachedDisDeleteEvent;
import j.jave.kernal.memcached.event.JMemcachedDisGetEvent;
import j.jave.kernal.memcached.event.JMemcachedDisSetEvent;
import j.jave.kernal.memcached.ext.JMemcacheInterfaceProvider;

import org.springframework.stereotype.Service;

@Service(value=DefaultMemcachedDelegateService.BEAN_NAME)
public class DefaultMemcachedDelegateService implements MemcachedDelegateService {
	
	public static final String BEAN_NAME="defaultMemcachedDelegateService";
	
	private JDefaultMemcachedDisService defaultMemcachedDisService
	=JServiceHubDelegate.get().getService(this, JMemcacheInterfaceProvider.get().getServiceInterface()); 

	@Override
	public Object set(String key, int expiry, Object value) {
		return defaultMemcachedDisService.set(key, expiry, value);
	}

	@Override
	public Object get(String key) {
		return defaultMemcachedDisService.get(key);
	}

	@Override
	public void add(String key, int expiry, Object value) {
		defaultMemcachedDisService.add(key, expiry, value);
	}

	@Override
	public void delete(String key) {
		defaultMemcachedDisService.delete(key);
	}
	
	@Override
	public Object trigger(JMemcachedDisGetEvent event) {
		return get(event.getKey());
	}

	@Override
	public Object trigger(JMemcachedDisSetEvent event) {
		return set(event.getKey(), event.getExpiry(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisDeleteEvent event) {
		delete(event.getKey());
		return true;
	}

	@Override
	public Object trigger(JMemcachedDisAddEvent event) {
		add(event.getKey(), event.getExpiry(), event.getValue());
		return true;
	}

	@Override
	public Object putNeverExpired(String key, Object object) {
		return defaultMemcachedDisService.putNeverExpired(key, object);
	}

	@Override
	public Object remove(String key) {
		return defaultMemcachedDisService.remove(key);
	}

	@Override
	public boolean contains(String key) {
		return defaultMemcachedDisService.contains(key);
	}

	@Override
	public Object put(String key, int expiry, Object value) {
		return defaultMemcachedDisService.put(key, expiry, value);
	}
}
