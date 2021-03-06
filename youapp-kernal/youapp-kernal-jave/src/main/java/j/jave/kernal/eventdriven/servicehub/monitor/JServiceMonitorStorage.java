package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.JProperties;
import j.jave.kernal.jave.service.JService;

/**
 * store any monitoring model, if any other custom one ( XML configuration) is not found, use {@link JDefaultServiceMonitorStorage} ad default.
 * @author J
 * @see JDefaultServiceMonitorStorage
 * @see JProperties#SERVICE_HUB_MONITOR_STATUS_STORAGE
 */
public interface JServiceMonitorStorage extends JService {

	void store(JEventProcessingStatus eventProcessingStatus);
	
	JEventProcessingStatus getEventProcessingStatus(String eventId);
	
}
