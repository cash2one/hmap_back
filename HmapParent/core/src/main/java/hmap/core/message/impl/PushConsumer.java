package hmap.core.message.impl;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.QueueMonitor;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.message.service.IHmsMessageCenter;
import org.springframework.beans.factory.annotation.Autowired;

@QueueMonitor(queue = "public.hmap.push.array")
public class PushConsumer implements IMessageConsumer<HmsReceivedMessageDTO> {
	@Autowired
	private IHmsMessageCenter messageCenter;

	@Override
	public void onMessage(HmsReceivedMessageDTO requestDto, String pattern) {

		if (pattern.equals("public.hmap.push.array")) {
			messageCenter.pushMessage(requestDto);
		}

	}
}

