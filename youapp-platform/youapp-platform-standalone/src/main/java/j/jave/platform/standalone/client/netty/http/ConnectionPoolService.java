package j.jave.platform.standalone.client.netty.http;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

class ConnectionPoolService {

	private static JLogger logger=JLoggerFactory.getLogger(ConnectionPoolService.class);
	
	private static ConcurrentHashMap<String, ConnectionService> 
	connectionServices=new ConcurrentHashMap<String, ConnectionService>();
	
	private static final ReentrantLock initializeConnectionLock=new ReentrantLock();
	
	public static ConnectionService get(String url) throws Exception{
		URI uri=new URI(url);
		String unique=DefaultConnectionService.getScheme(uri)+"//"+
				DefaultConnectionService.getHost(uri)+":"+
				DefaultConnectionService.getPort(uri);
		
		if(connectionServices.containsKey(unique)){
			if(logger.isDebugEnabled()){
				logger.debug("got keep-alive connection from pool : "+url);
			}
			return connectionServices.get(unique);
		}
		initializeConnectionLock.lockInterruptibly();
		try{
			ConnectionService connectionService=  new KeepAliveConnectionService();
			connectionServices.put(unique, connectionService);
			return connectionService;
		}finally{
			if(initializeConnectionLock.isHeldByCurrentThread()){
				initializeConnectionLock.unlock();
			}
		}
	}
    
}