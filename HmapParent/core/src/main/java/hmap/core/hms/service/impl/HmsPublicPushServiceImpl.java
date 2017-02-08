package hmap.core.hms.service.impl;

import com.hand.hap.message.IMessagePublisher;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.service.IHmsPublicPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HmsPublicPushServiceImpl
		implements IHmsPublicPushService {
	@Autowired
	IMessagePublisher messagePublisher;

	private Logger logger = LoggerFactory
			.getLogger(HmsPublicPushServiceImpl.class);

	@Override
	public void push(HmsReceivedMessageDTO requestDto) {
        messagePublisher.rPush("public.hmap.push.array",requestDto);
        if (logger.isDebugEnabled()){
            logger.debug("publish index");
        }
	}
}
