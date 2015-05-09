/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import j.jave.framework.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JMemcachedDisDeleteListener.class)
public class JMemcachedDisDeleteEvent extends JMemcachedDisEvent<JMemcachedDisDeleteEvent> {

	public JMemcachedDisDeleteEvent(Object source, String key) {
		super(source, key, null, 0, TYPE.DELETE);
	}

}
