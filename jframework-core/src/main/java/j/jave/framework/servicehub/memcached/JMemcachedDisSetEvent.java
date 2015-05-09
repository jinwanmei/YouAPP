/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JMemcachedDisSetListener.class)
public class JMemcachedDisSetEvent extends JMemcachedDisEvent<JMemcachedDisSetEvent> {

	/**
	 * @param source
	 * @param priority
	 * @param unique
	 * @param key
	 * @param value
	 * @param expiry
	 * @param type
	 */
	public JMemcachedDisSetEvent(Object source, int priority, String unique,
			String key, Object value, int expiry) {
		super(source, priority, unique, key, value, expiry, TYPE.SET);
	}

	/**
	 * the expiry set to 0
	 * @param source
	 * @param unique
	 * @param key
	 * @param value
	 */
	public JMemcachedDisSetEvent(Object source, String unique,
			String key, Object value) {
		super(source, JAPPEvent.NORMAL, unique, key, value, 0, TYPE.SET);
	}
	
	/**
	 * 
	 * @param source
	 * @param unique
	 * @param key
	 * @param value
	 * @param expiry
	 */
	public JMemcachedDisSetEvent(Object source, String unique,
			String key, Object value,int expiry) {
		super(source, JAPPEvent.NORMAL, unique, key, value, expiry, TYPE.SET);
	}

}
