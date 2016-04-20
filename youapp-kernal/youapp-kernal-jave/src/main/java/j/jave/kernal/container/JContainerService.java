package j.jave.kernal.container;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.container.eventdriven.JContainerGetEvent;
import j.jave.kernal.container.eventdriven.JContainerGetListener;
import j.jave.kernal.container.eventdriven.JContainerRegisterEvent;
import j.jave.kernal.container.eventdriven.JContainerRegisterListener;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

class JContainerService extends JServiceFactorySupport<JContainerService> 
	implements JService, JContainerRegisterListener ,JContainerGetListener{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JContainerService.class);
	
	private static JContainerService containerService=new JContainerService(JConfiguration.get());
	
	@Override
	public JContainerService getService() {
		return containerService;
	}
	private JContainerService(JConfiguration configuration) {
	}
	
	private Map<String, JContainer> containers=new ConcurrentHashMap<String, JContainer>();
	
	private ReentrantLock lock=new ReentrantLock();
	
	@Override
	public Object trigger(JContainerRegisterEvent event) {
		try{
			lock.lockInterruptibly();
			JContainer container=event.getContainer();
			if(containers.keySet().contains(container.unique())){
				throw new JInitializationException("container["+container.unique()+"] already exists.");
			}
			containers.put(container.unique(), container);
			return true;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}finally{
			lock.unlock();
		}
		return false;
	}
	@Override
	public Object trigger(JContainerGetEvent event) {
		String unique=event.getContainerUnique();
		return containers.get(unique);
	}
	
}
