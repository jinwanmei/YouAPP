/**
 * 
 */
package j.jave.platform.webcomp.access.subhub;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.webcomp.core.service.SessionUser;

/**
 * any sub-class implementation can provide the SessionUserService .
 * <strong>Note that the system must implement the interface via registering the service in the service hub to enable user session.</strong>
 * @author J
 * @see SessionUserService
 * @see JServiceHubDelegate
 */
@JEventOnListener(name=SessionUserGetEvent.class)
public interface SessionUserGetListener extends JYouAPPListener {
	SessionUser trigger(SessionUserGetEvent event); 
}
