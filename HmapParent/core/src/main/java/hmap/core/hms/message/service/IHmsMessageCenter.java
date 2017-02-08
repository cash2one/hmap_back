package hmap.core.hms.message.service;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;

public interface IHmsMessageCenter {
	public void pushMessage(HmsReceivedMessageDTO requestDto);
}
