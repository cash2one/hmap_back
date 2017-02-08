package hmap.core.hms.message.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.message.dto.MessageReturnDTO;
import hmap.core.hms.message.service.IHmsReceivedMessageService;
import hmap.core.security.SecurityUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/i/api/message")
public class HmsReceivedMessageController extends BaseController {
	@Autowired
	private IHmsReceivedMessageService receivedMessageService;

	@RequestMapping(value = "/summary")
	@ResponseBody
	@Timed
	public MessageReturnDTO querySummary(HttpServletRequest request) {
		String userName = SecurityUtils.getCurrentUserLogin();
				MessageReturnDTO messageReturnDto = null;
		try {
			messageReturnDto = receivedMessageService.getBriefMessage(userName);
		} catch (Exception e) {
			e.printStackTrace();
			messageReturnDto = new MessageReturnDTO("N", null);
		}
		return messageReturnDto;
	}

	@RequestMapping("/readAllByType")
	@ResponseBody
	@Timed
	public ResponseData readAll(HttpServletRequest request,
			@RequestBody JSONObject jsonObj) {
		String userName = SecurityUtils.getCurrentUserLogin();
		String messageType = jsonObj.getString("MessageTypeCode");
		if(receivedMessageService.readAllByType(userName, messageType)){
			return new ResponseData(true);
		}
		return new ResponseData(false);
	}

	@RequestMapping("/detail")
	@ResponseBody
	@Timed
	public MessageReturnDTO queryDetail(HttpServletRequest request,
			@RequestBody JSONObject jsonObj) {
		String userName = SecurityUtils.getCurrentUserLogin();
		MessageReturnDTO messageReturnDto = null;
		try {
			messageReturnDto = receivedMessageService.getDetailMessage(userName,
					jsonObj.getString("messageType"),jsonObj.getString("page"));
		} catch (Exception e) {
			e.printStackTrace();
			messageReturnDto = new MessageReturnDTO("N", null);
		}
		return messageReturnDto;
	}

	@RequestMapping("/process")
	@ResponseBody
	@Timed
	public MessageReturnDTO processBatch(HttpServletRequest request,
			@RequestBody JSONObject jsonObj) {
		String userName = SecurityUtils.getCurrentUserLogin();
		JSONArray array = jsonObj.getJSONArray("messageList");
		MessageReturnDTO messageReturnDTO = null;
		for (int i = 0; i < array.size(); i++) {
			JSONObject o = (JSONObject) array.get(i);
			if (o.getString("actionCode").toLowerCase().equals("delete")) {
				messageReturnDTO = receivedMessageService.deleteMessage(o.getString("messageId"));
			} else if (o.getString("actionCode").toLowerCase().equals("change")) {
				messageReturnDTO = receivedMessageService.changeMessage(o.getString("messageId"),userName);
			}
		}

		return messageReturnDTO;
	}

	@RequestMapping("/messageCenter")
	@ResponseBody
	@Timed
	public Object message(HttpServletRequest request,
			@RequestBody JSONObject jsonObj) {
		String userName = SecurityUtils.getCurrentUserLogin();
		MessageReturnDTO messageReturnDto = null;
		String interfaceCode  = jsonObj.getString("interfaceCode").toLowerCase();
		if (interfaceCode.equals("summary")) {
			try {
				messageReturnDto = receivedMessageService
						.getBriefMessage(userName);
			} catch (Exception e) {
				e.printStackTrace();
				messageReturnDto = new MessageReturnDTO("N", null);
			}
			return messageReturnDto;
		} else if (interfaceCode.equals("detail")) {
			try {
				messageReturnDto = receivedMessageService.getDetailMessage(
						userName, jsonObj.getString("messageType"),jsonObj.getString("page"));
			} catch (Exception e) {
				e.printStackTrace();
				messageReturnDto = new MessageReturnDTO("N", null);
			}
			return messageReturnDto;
		}else if (interfaceCode.equals("readallbytype")) {
			String messageType = jsonObj.getString("MessageTypeCode");
			if(receivedMessageService.readAllByType(userName, messageType)){
				return new ResponseData(true);
			}
			return new ResponseData(false);
		}  else {
			try {
				JSONArray array = jsonObj.getJSONArray("messageList");
				MessageReturnDTO messageReturnDTO = null;
				for (int i = 0; i < array.size(); i++) {
					JSONObject o = (JSONObject) array.get(i);
					if (o.getString("actionCode").toLowerCase()
							.equals("delete")) {
						messageReturnDTO = receivedMessageService.deleteMessage(o
								.getString("messageId"));
					} else if (o.getString("actionCode").toLowerCase()
							.equals("change")) {
						messageReturnDTO = receivedMessageService.changeMessage(o
								.getString("messageId"),userName);
					}
				}
				return messageReturnDTO;
			} catch (Exception e) {
				e.printStackTrace();
				messageReturnDto = new MessageReturnDTO("N", null);
			}
			return messageReturnDto;
		}
	}
}
