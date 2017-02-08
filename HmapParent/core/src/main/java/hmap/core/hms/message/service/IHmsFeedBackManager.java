package hmap.core.hms.message.service;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;

public interface IHmsFeedBackManager {
	public void feedBack(HmsReceivedMessageDTO requestDto);
}
