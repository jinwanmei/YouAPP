/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.ehcache.subhub;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.ehcache.JDefaultEhcacheService;
import j.jave.kernal.ehcache.JEhcacheServiceAware;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.platform.basicsupportcomp.support.ehcache.subhub.EhcacheServiceFactory")
public class EhcacheServiceFactory extends SpringServiceFactorySupport<EhcacheDelegateService> {
	
	public EhcacheServiceFactory() {
		super(EhcacheDelegateService.class);
	}
	
	@Autowired(required=false)
	private SpringEhcacheConfiguration springEhcacheConfiguration;
	
	private EhcacheDelegateService ehcacheService;
	
	private Object sync=new Object();
	
	@Override
	public EhcacheDelegateService getService() {
		
		if(ehcacheService==null){
			synchronized (sync) {
				if(ehcacheService==null){
					// Ehcache may be held by spring. 
					if(springEhcacheConfiguration!=null){
						String beanName=springEhcacheConfiguration.getEhcacheBeanName();
						JAssert.isNotEmpty(beanName);
						Object bean=getBeanByName(beanName,Object.class);
						if(FactoryBean.class.isInstance(bean)){
							// its factory bean.
							FactoryBean factoryBean=(FactoryBean) bean;
							Ehcache cache=null;
							try {
								cache= (Ehcache) factoryBean.getObject();
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								throw new RuntimeException(e);
							}
							
							SpringEhcacheAware defaultSpringBeanEhcacheServiceImpl=
									getBeanByName(DefaultSpringBeanEhcacheServiceImpl.BEAN_NAME, 
											SpringEhcacheAware.class);
							defaultSpringBeanEhcacheServiceImpl.putEhcache(cache);
							this.ehcacheService= (EhcacheDelegateService) defaultSpringBeanEhcacheServiceImpl;
							return this.ehcacheService;
						}
						else{
							LOGGER.info("cache bean not found:"+bean.getClass().getName());
						}
					
					}
					else{
						// use default .
						JEhcacheServiceAware defaultEhcacheServiceImpl=
								getBeanByName(DefaultEhcacheServiceImpl.class.getName(), JEhcacheServiceAware.class);
						JDefaultEhcacheService defaultEhcacheService=new JDefaultEhcacheService(JConfiguration.get());
						defaultEhcacheServiceImpl.setEhcacheService(defaultEhcacheService);
						this.ehcacheService= (EhcacheDelegateService) defaultEhcacheServiceImpl;
						return this.ehcacheService;
					}
				}
			}
		}
		return ehcacheService;
	}
	
	@Override
	public String getName() {
		return "Ehcache Provider";
	}
	
}
