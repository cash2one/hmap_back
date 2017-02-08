package hmap.core.hms.message.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.service.IHmsPublicPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/push")
public class HmsPublicPushController extends BaseController {
	@Autowired
	private IHmsPublicPushService publicPushService;


	@RequestMapping(value = "/doPush")
	@ResponseBody
	@Timed
	public ResponseData push(HttpServletRequest request,
			@RequestBody(required = true) HmsReceivedMessageDTO body) {
		publicPushService.push(body);
		return new ResponseData();
	}



}
