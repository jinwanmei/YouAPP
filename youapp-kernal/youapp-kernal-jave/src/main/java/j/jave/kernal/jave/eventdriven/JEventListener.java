package j.jave.kernal.jave.eventdriven;

import java.util.EventListener;

/**
 * basic listener which all listeners should extend, provides the boundary to other system.
 * all sub-class should can be called on the below method
 *<p><strong>public Object trigger(JEventObject event)</strong>;
 *<p>
 * @author J
 */
public interface JEventListener extends EventListener {


	
}
