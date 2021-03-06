/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.weburl;

import j.jave.kernal.jave.support.resourceuri.ResourceCacheService;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheServiceGetListener;



/**
 * system resource interface , to used in request response cached. 
 * @author J
 */
public interface WebRequestURLCacheService extends ResourceCacheService<WebRequestURLCacheModel>, ResourceCacheServiceGetListener{
	
	boolean isNeedCache(String path);
}
