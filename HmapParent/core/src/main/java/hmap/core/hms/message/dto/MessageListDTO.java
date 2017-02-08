package hmap.core.hms.message.dto;

import java.util.List;
import java.util.Map;

public class MessageListDTO {
	private String messageId;
	private String pushMessageId;
	private String messageTypeCode;
	private String messageTypeDesc;
	private String messageContent;
	private List<Map<String,String>> messageDetail;
	private List<Map<String,String>> messageSecret;
	private String messageNum;
	private String status;
	private String sendTime;
	private String latestMessageTime;
	private String compareTime;
	
	
	
	
	
	public String getCompareTime() {
		return compareTime;
	}
	public void setCompareTime(String compareTime) {
		this.compareTime = compareTime;
	}
	public String getLatestMessageTime() {
		return latestMessageTime;
	}
	public void setLatestMessageTime(String latestMessageTime) {
		this.latestMessageTime = latestMessageTime;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Map<String, String>> getMessageSecret() {
		return messageSecret;
	}
	public void setMessageSecret(List<Map<String, String>> messageSecret) {
		this.messageSecret = messageSecret;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getPushMessageId() {
		return pushMessageId;
	}
	public void setPushMessageId(String pushMessageId) {
		this.pushMessageId = pushMessageId;
	}
	public String getMessageTypeCode() {
		return messageTypeCode;
	}
	public void setMessageTypeCode(String messageTypeCode) {
		this.messageTypeCode = messageTypeCode;
	}
	public String getMessageTypeDesc() {
		return messageTypeDesc;
	}
	public void setMessageTypeDesc(String messageTypeDesc) {
		this.messageTypeDesc = messageTypeDesc;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(String messageNum) {
		this.messageNum = messageNum;
	}
	public List<Map<String, String>> getMessageDetail() {
		return messageDetail;
	}
	public void setMessageDetail(List<Map<String, String>> messageDetail) {
		this.messageDetail = messageDetail;
	}

	@Override
	public String toString() {
		return "MessageListDTO{" +
				"messageId='" + messageId + '\'' +
				", pushMessageId='" + pushMessageId + '\'' +
				", messageTypeCode='" + messageTypeCode + '\'' +
				", messageTypeDesc='" + messageTypeDesc + '\'' +
				", messageContent='" + messageContent + '\'' +
				", messageDetail=" + messageDetail +
				", messageSecret=" + messageSecret +
				", messageNum='" + messageNum + '\'' +
				", status='" + status + '\'' +
				", sendTime='" + sendTime + '\'' +
				", latestMessageTime='" + latestMessageTime + '\'' +
				", compareTime='" + compareTime + '\'' +
				'}';
	}
}
