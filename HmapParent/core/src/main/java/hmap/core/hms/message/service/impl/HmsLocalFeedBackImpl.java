package hmap.core.hms.message.service.impl;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.feedback.mapper.HmsReceivedMessageMapper;
import hmap.core.hms.feedback.service.IHmsFeedBack;

import javax.annotation.Resource;

public class HmsLocalFeedBackImpl implements IHmsFeedBack {
	@Resource
	private HmsReceivedMessageMapper receivedMessageMapper;
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void synPushInfo(HmsReceivedMessageDTO requestDto) {
		receivedMessageMapper.insertSelective(requestDto);
	}

}
