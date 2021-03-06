/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * any sub-class implementation can provide the ResourceCachedService . generally it's used together with ResourceCachedServiceFactory
 * <p><strong>Note that the concrete system must implement the interface if the resource cached function enabled.</strong>
 * @author J
 * @see WebRequestURLCacheServiceFactory
 */
@JEventOnListener(name=ResourceCacheServiceGetEvent.class)
public interface ResourceCacheServiceGetListener extends JYouAPPListener {
	ResourceCacheService trigger(ResourceCacheServiceGetEvent event); 
}
