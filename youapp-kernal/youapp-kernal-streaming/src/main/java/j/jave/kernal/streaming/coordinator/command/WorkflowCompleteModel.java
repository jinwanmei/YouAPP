package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.streaming.coordinator.NodeStatus;

public class WorkflowCompleteModel extends BaseCommandModel {

	/**
	 * the status of workflow instance , maybe any one of {@link NodeStatus}
	 * @see NodeStatus
	 */
	private NodeStatus status;

	public NodeStatus getStatus() {
		return status;
	}

	public void setStatus(NodeStatus status) {
		this.status = status;
	}
	
}
