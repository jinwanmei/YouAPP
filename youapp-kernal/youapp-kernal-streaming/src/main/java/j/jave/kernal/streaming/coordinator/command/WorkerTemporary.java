package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.streaming.coordinator.WorkerExecutingPathVal;

public class WorkerTemporary implements JModel {

	private WorkerExecutingPathVal workerExecutingPathVal;
	
	private String tempPath;

	public WorkerExecutingPathVal getWorkerExecutingPathVal() {
		return workerExecutingPathVal;
	}

	public void setWorkerExecutingPathVal(WorkerExecutingPathVal workerExecutingPathVal) {
		this.workerExecutingPathVal = workerExecutingPathVal;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
}
