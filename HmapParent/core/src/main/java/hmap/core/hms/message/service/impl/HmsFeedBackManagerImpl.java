package hmap.core.hms.message.service.impl;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.feedback.service.IHmsFeedBack;
import hmap.core.hms.message.service.IHmsFeedBackManager;

import java.util.List;

public class HmsFeedBackManagerImpl implements IHmsFeedBackManager{
	private List<IHmsFeedBack> list;

	public List<IHmsFeedBack> getList() {
		return list;
	}

	public void setList(List<IHmsFeedBack> list) {
		this.list = list;
	}


	@Override
	public void feedBack(HmsReceivedMessageDTO requestDto) {
		// TODO Auto-generated method stub
		for(IHmsFeedBack ifb:list){
			if(ifb.getName().equals(requestDto.getSourceSystem()) || ifb.getName().equals("local")){
				ifb.synPushInfo(requestDto);
			}
		}
	}
	

}
