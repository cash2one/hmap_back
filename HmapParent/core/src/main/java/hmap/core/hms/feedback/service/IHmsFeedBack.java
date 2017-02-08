package hmap.core.hms.feedback.service;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;

public interface IHmsFeedBack {
	public void synPushInfo(HmsReceivedMessageDTO requestDto);
	public String getName();
}
