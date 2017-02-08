package hmap.core.hms.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

public class MessageReturnDTO {
	@JsonInclude(Include.NON_NULL)
	private String returnCode;
	@JsonInclude(Include.NON_NULL)
	private String returnMsg;
	@JsonInclude(Include.NON_NULL)
	private List<MessageListDTO> returnData;
	@JsonInclude(Include.NON_NULL)
	private String latestTime;
	
	public String getLatestTime() {
		return latestTime;
	}
	public void setLatestTime(String latestTime) {
		this.latestTime = latestTime;
	}
	public MessageReturnDTO(String returnCode,List<MessageListDTO> returnData) {
		super();
		this.returnCode = returnCode;
		if(returnCode.equals("S")){
			this.returnMsg = "获取消息成功";
		}else{
			this.returnMsg = "获取消息失败";
		}
		this.returnData = returnData;
	}
	public MessageReturnDTO(String returnCode,String msg,List<MessageListDTO> returnData) {
		super();
		this.returnCode = returnCode;
		if(returnCode.equals("S")){
			this.returnMsg = msg;
		}else{
			this.returnMsg = "操作失败";
		}
		this.returnData = returnData;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public List<MessageListDTO> getReturnData() {
		return returnData;
	}
	public void setReturnData(List<MessageListDTO> returnData) {
		this.returnData = returnData;
	}
	@Override
	public String toString() {
		return "MessageReturnDTO [returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + ", returnData=" + returnData + "]";
	}
	
	
}
