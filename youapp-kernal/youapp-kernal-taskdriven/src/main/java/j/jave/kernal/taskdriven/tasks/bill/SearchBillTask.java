package j.jave.kernal.taskdriven.tasks.bill;

import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.taskdriven.tkdd.JBaseTask;
import j.jave.kernal.taskdriven.tkdd.JTaskContext;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;


@JTaskMetadataHierarchy
@JTaskMetadataOnTask(value=SearchBillMetadata.class)
public class SearchBillTask extends JBaseTask {
	
	public SearchBillTask(JTaskContext taskContext) {
		super(taskContext);
	}

	@Override
	public Object run() {
		JLoggerFactory.getLogger(this.getClass()).info(this.getClass().getName());
		return null;
	}


}
