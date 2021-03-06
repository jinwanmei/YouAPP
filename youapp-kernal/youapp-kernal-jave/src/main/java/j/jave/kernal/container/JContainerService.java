package j.jave.kernal.container;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.container.listener.JContainerGetEvent;
import j.jave.kernal.container.listener.JContainerGetListener;
import j.jave.kernal.container.listener.JContainerRegisterEvent;
import j.jave.kernal.container.listener.JContainerRegisterListener;
import j.jave.kernal.container.listener.JContainerUniquesGetEvent;
import j.jave.kernal.container.listener.JContainerUniquesGetListener;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * the container service interfaces, all contains register themselves here.
 * Any function on the container can be register in the service hub via event-listener mechanism.
 * @author JIAZJ
 * @see JContainerGetListener
 * @see JContainerRegisterListener
 * @see JContainerUniquesGetListener
 */
public class JContainerService 
	implements JService, JContainerRegisterListener ,JContainerGetListener,JContainerUniquesGetListener{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JContainerService.class);
	
	public JContainerService(JConfiguration configuration) {
		
	}
	
	public JContainerService() {
	}
	
	private Map<String, JContainer> containers=new ConcurrentHashMap<String, JContainer>();
	
	private ReentrantLock lock=new ReentrantLock();
	
	@Override
	public Object trigger(JContainerRegisterEvent event) {
		try{
			lock.lockInterruptibly();
			JContainer container=event.getContainer();
			if(containers.keySet().contains(container.unique())){
				LOGGER.info("container["+container.unique()+"] already exists, be replace");
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
	@Override
	public Object trigger(JContainerUniquesGetEvent event) {
		return Collections.unmodifiableCollection(containers.keySet());
	}
	
}
