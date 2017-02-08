package hmap.core.hms.message.dto;

public class MessageDetailDTO {
	private String recordId;
	private String workflowId;
	private String nodeId;
	private String instanceId;
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Override
	public String toString() {
		return "MessageDetailDTO{" +
				"recordId='" + recordId + '\'' +
				", workflowId='" + workflowId + '\'' +
				", nodeId='" + nodeId + '\'' +
				", instanceId='" + instanceId + '\'' +
				'}';
	}
}
