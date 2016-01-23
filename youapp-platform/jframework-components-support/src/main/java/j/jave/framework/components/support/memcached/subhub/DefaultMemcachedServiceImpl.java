package j.jave.framework.components.support.memcached.subhub;

import j.jave.framework.commons.memcached.JDefaultMemcachedDisService;
import j.jave.framework.commons.memcached.JMemcachedDisService;
import j.jave.framework.commons.memcached.JMemcachedDisServiceAware;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisAddEvent;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisDeleteEvent;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisSetEvent;

import org.springframework.stereotype.Service;

@Service(value="defaultMemcachedServiceImpl")
public class DefaultMemcachedServiceImpl implements MemcachedService,JMemcachedDisServiceAware {
	
	private JDefaultMemcachedDisService defaultMemcachedDisService; 

	@Override
	public void setMemcachedDisService(JMemcachedDisService memcachedDisService) {
		this.defaultMemcachedDisService = (JDefaultMemcachedDisService) memcachedDisService;
	}
	
	@Override
	public JMemcachedDisService getMemcachedDisService() {
		return this.defaultMemcachedDisService;
	}
	
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
}