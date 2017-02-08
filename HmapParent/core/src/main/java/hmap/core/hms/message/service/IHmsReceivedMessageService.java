package hmap.core.hms.message.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.message.dto.MessageReturnDTO;

public interface IHmsReceivedMessageService extends IBaseService<HmsReceivedMessageDTO>, ProxySelf<IHmsReceivedMessageService>{
	public MessageReturnDTO getBriefMessage(String empNo);
	public MessageReturnDTO getDetailMessage(String empNo, String type, String page);
	public MessageReturnDTO deleteMessage(String id);
	public MessageReturnDTO changeMessage(String id, String empNo);
	public boolean readAllByType(String empNo, String type);
}
