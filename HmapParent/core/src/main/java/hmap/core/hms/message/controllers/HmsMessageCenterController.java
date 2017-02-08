package hmap.core.hms.message.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.message.domain.HmsMessageTemplate;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.message.service.IHmsMessageCenter;
import hmap.core.hms.message.service.IHmsMessageTemplateService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by shanhd on 2016/11/24.
 */
@Controller
public class HmsMessageCenterController extends BaseController {

    @Resource(name = "messageCenter")
    private IHmsMessageCenter messageCenter;

    @Autowired
    private IHmsMessageTemplateService hmsMessageTemplateService;


    @RequestMapping(value = "/api/messageCenter/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData sendMessage(HttpServletRequest request, @RequestBody(required = false) JSONObject jsonObject) {

          IRequest iRequest=createRequestContext(request);
          String pushMethod = jsonObject.getString("pushMethod");
          String jpushType = jsonObject.getString("jpushType");
          String sendObject = jsonObject.getString("sendObject");
          String templateId = jsonObject.getString("templateId");
          JSONObject contentVar= jsonObject.getJSONObject("contentVar");
        HmsMessageTemplate template=new HmsMessageTemplate();
        template.setId(templateId);
        template=hmsMessageTemplateService.selectByPrimaryKey(iRequest,template);

          HmsReceivedMessageDTO  dto=new HmsReceivedMessageDTO();
          dto.setPushMethod(pushMethod);
          dto.setAudienceType(jpushType);
          dto.setReceiverList(sendObject.split(","));
          dto.setAttribute1(templateId);
          if("jPush".equals(pushMethod)){

          }else if("sms".equals(pushMethod)) {
              dto.setAttribute1(template.getTemplateId());
              dto.setContent(contentVar.toString());
          }else if("email".equals(pushMethod)){

          }
          messageCenter.pushMessage(dto);

        return new ResponseData();

    }

}
