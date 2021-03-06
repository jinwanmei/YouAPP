/**
 * 
 */
package j.jave.platform.sps.core.servicehub;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

import org.springframework.context.ApplicationContext;

/**
 * @author J
 */
@JListenerOnEvent(name=SpringApplicationContextInitializedListener.class)
public class SpringApplicationContextInitializedEvent extends JYouAPPEvent<SpringApplicationContextInitializedEvent> {
	
	private final JConfiguration configuration;
	
	private ApplicationContext applicationContext;
	
	public SpringApplicationContextInitializedEvent(Object source,JConfiguration configuration) {
		super(source);
		this.configuration=configuration;
	}

	public SpringApplicationContextInitializedEvent(Object source,int priority ,JConfiguration configuration) {
		super(source,priority);
		this.configuration=configuration;
	}
	
	public JConfiguration getConfiguration() {
		return configuration;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
}
