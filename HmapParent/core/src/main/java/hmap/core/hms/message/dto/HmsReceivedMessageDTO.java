package hmap.core.hms.message.dto;

import com.hand.hap.system.dto.BaseDTO;
import net.sf.json.JSONArray;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "hms_received_message")
public class HmsReceivedMessageDTO extends BaseDTO {
	@Id
	private String id;
	private String receiver;
	private String status;
	private String content;
	private String pushMethod;
	private String messageType;
	private String returnId;
	private String sourceSystem;
	private Date startTime;
	private Long duration;
	private String errorMessage;
	private Long instanceId;
	private String sourceInstanceId;
	private String sourceNodeId;
	private String sourceRecordId;
	private String sourceWorkflowId;
	private String attribute1;
	private String attribute2;
	private String senderCode;
	private String contentDesc;
	private String audienceType;
	private Object receiverList;
	private String apnsProduction;


	public String getApnsProduction() {
		return apnsProduction;
	}

	public void setApnsProduction(String apnsProduction) {
		this.apnsProduction = apnsProduction;
	}

	public HmsReceivedMessageDTO(){
		setAudienceType("tag");
	}


	public String getReceiverList() {
		return JSONArray.fromObject(receiverList).toString();
	}

	public void setReceiverList(Object receiverList) {
		this.receiverList = receiverList;
	}

	public String getAudienceType() {
		return audienceType;
	}

	public void setAudienceType(String audienceType) {
		this.audienceType = audienceType;
	}
	
	public String getSenderCode() {
		return senderCode;
	}
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}
	public String getContentDesc() {
		return contentDesc;
	}
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}
	public Long getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPushMethod() {
		return pushMethod;
	}
	public void setPushMethod(String pushMethod) {
		this.pushMethod = pushMethod;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSourceInstanceId() {
		return sourceInstanceId;
	}
	public void setSourceInstanceId(String sourceInstanceId) {
		this.sourceInstanceId = sourceInstanceId;
	}
	public String getSourceNodeId() {
		return sourceNodeId;
	}
	public void setSourceNodeId(String sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}
	public String getSourceRecordId() {
		return sourceRecordId;
	}
	public void setSourceRecordId(String sourceRecordId) {
		this.sourceRecordId = sourceRecordId;
	}
	public String getSourceWorkflowId() {
		return sourceWorkflowId;
	}
	public void setSourceWorkflowId(String sourceWorkflowId) {
		this.sourceWorkflowId = sourceWorkflowId;
	}
	@Override
	public String toString() {
		return "HrmsReceivedMessageDto [id=" + id + ", receiver=" + receiver
				+ ", status=" + status + ", content=" + content
				+ ", pushMethod=" + pushMethod + ", messageType=" + messageType
				+ ", returnId=" + returnId + ", sourceSystem=" + sourceSystem
				+ ", startTime=" + startTime + ", duration=" + duration
				+ ", errorMessage=" + errorMessage + ", sourceInstanceId="
				+ sourceInstanceId + ", sourceNodeId=" + sourceNodeId
				+ ", sourceRecordId=" + sourceRecordId + ", sourceWorkflowId="
				+ sourceWorkflowId + ", attribute1=" + attribute1
				+ ", attribute2=" + attribute2 + "]";
	}
	
	
}
