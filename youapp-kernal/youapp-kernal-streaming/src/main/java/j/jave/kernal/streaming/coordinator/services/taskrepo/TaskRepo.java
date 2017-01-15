package j.jave.kernal.streaming.coordinator.services.taskrepo;

import j.jave.kernal.streaming.coordinator.Task;
import me.bunny.kernel.jave.service.JService;

public interface TaskRepo extends JService{

	String addTask(Task task);
	
	/**
	 * retrieve but not remove the task
	 * @param id
	 * @return
	 */
	Task peek(String taskId);
	
	/**
	 * retrieve and remove the task if existing in the repo
	 * @param workflowName
	 * @return
	 */
	Task getTaskByWorfklowName(String workflowName);
	
}