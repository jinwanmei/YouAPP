package j.jave.kernal.ehcache;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

public class JDefaultEhcacheServiceFactory extends JServiceFactorySupport<JDefaultEhcacheService> {

	private JDefaultEhcacheService defaultEhcacheService=new JDefaultEhcacheService(JConfiguration.get());
	
	@Override
	public JDefaultEhcacheService getService() {
		return defaultEhcacheService;
	}
	
}
