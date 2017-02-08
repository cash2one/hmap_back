package hmap.core.hms.service;

import com.hand.hap.core.ProxySelf;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;

public interface IHmsPublicPushService extends  ProxySelf<IHmsPublicPushService>{
	public void push(HmsReceivedMessageDTO body);
}
