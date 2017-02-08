package hmap.core.hms.message.service.impl;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.feedback.mapper.HmsReceivedMessageMapper;
import hmap.core.hms.message.service.IHmsFeedBackManager;
import hmap.core.hms.message.service.IHmsMessageCenter;
import hmap.core.hms.message.service.IHmsPush;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HmsMessageCenterImpl implements IHmsMessageCenter {
	private Logger logger = LoggerFactory.getLogger(HmsMessageCenterImpl.class);
	@Autowired
	private IHmsFeedBackManager feedBackManager;
	@Resource
	private HmsReceivedMessageMapper receivedMessageMapper;

	private List<IHmsPush> pushList;

	public List<IHmsPush> getPushList() {
		return pushList;
	}

	public void setPushList(List<IHmsPush> pushList) {
		this.pushList = pushList;
	}

	@Override
	public void pushMessage(HmsReceivedMessageDTO requestDto) {
		// TODO Auto-generated method stub

		String id = UUID.randomUUID().toString();
		requestDto.setId(id);
		String result = null;

		if (requestDto.getPushMethod() == null) {
			requestDto.setPushMethod("jPush");
		}


		for (IHmsPush ipush : pushList) {
			if (ipush.getName().equals(requestDto.getPushMethod())) {
				try {
					Date startDate = new Date();
					result = ipush.doPush(requestDto);
					requestDto.setStartTime(startDate);
					if (result.indexOf("error") != -1
							|| result.equals("503")) {
						requestDto.setErrorMessage(result);
						requestDto.setStatus("ERROR");
						logger.error(result);
					} else {
						requestDto.setStatus("NOREAD");
						requestDto.setDuration(new Date().getTime()
								- startDate.getTime());
						requestDto.setReturnId(result);
					}
					// 回写
					feedBackManager.feedBack(requestDto);
				} catch (Exception e) {
					e.printStackTrace();
					requestDto.setErrorMessage("503");
					requestDto.setStatus("ERROR");
					feedBackManager.feedBack(requestDto);
				} finally {
					// receivedMessageMapper.insertSelective(requestDto);
				}
			}
		}
	}

}
