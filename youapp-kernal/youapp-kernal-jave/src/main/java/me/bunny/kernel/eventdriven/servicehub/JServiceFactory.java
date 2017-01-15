/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;

import me.bunny.kernel._c.service.JService;

/**
 * any service provider need implement the factory to expose self.
 * @author J
 *
 */
public interface JServiceFactory<T extends JService> {
	/**
	 * get service object. 
	 * @return
	 */
	T getService();	
	
	/**
	 * get service class. 
	 * @return
	 */
	Class<?> getServiceClass();
	
	/**
	 * get service implementation class. 
	 * @return
	 */
	Class<?> getServiceImplClass();
	
	/**
	 * get service name.
	 * @return
	 */
	String getName();
	
	/**
	 * get unique service identification .
	 * @return
	 */
	String getUniqueId();
	
	/**
	 * describer 
	 * @return
	 */
	String describer();
	
	/**
	 * test if the factory is available or not.
	 * @return
	 */
	boolean available();
	
}
