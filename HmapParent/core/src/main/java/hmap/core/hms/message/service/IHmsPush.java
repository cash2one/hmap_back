package hmap.core.hms.message.service;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;

public interface IHmsPush {
	public String getName();
	public String doPush(HmsReceivedMessageDTO requestDto);
}
