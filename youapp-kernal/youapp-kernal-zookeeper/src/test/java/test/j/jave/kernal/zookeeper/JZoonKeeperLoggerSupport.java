package test.j.jave.kernal.zookeeper;

import org.apache.zookeeper.WatchedEvent;

import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

public class JZoonKeeperLoggerSupport {

	protected static final JLogger LOGGER=JLoggerFactory.getLogger(JZoonKeeperLoggerSupport.class);
	
	public static void loggerWatchedEvent(WatchedEvent watchedEvent,String prefix){
		LOGGER.info(prefix+" : "+JJSON.get().formatObject(watchedEvent));
	}
	
	public static void loggerWatchedEvent(WatchedEvent watchedEvent){
		loggerWatchedEvent(watchedEvent, "DEFAULT");
	}
	
}
