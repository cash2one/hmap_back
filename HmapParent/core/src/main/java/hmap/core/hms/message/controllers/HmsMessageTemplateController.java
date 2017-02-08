package hmap.core.hms.message.controllers;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.CreateTemplateRequest;
import com.baidubce.services.sms.model.CreateTemplateResponse;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.message.domain.HmsMessageTemplate;
import hmap.core.hms.message.service.IHmsMessageTemplateService;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanhd on 2016/11/29.
 */
@Controller
public class HmsMessageTemplateController extends BaseController {

    @Autowired
    private IHmsMessageTemplateService hmsMessageTemplateService;
    @Autowired
    private IHmsSystemConfigService systemConfigService;

    @RequestMapping(value = {"/api/messageTemplate/query"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseData query(HttpServletRequest request){

        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize =Integer.parseInt(request.getParameter("pagesize"));
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(hmsMessageTemplateService.query(page,pageSize,iRequest));
    }

    @RequestMapping(value = {"/api/messageTemplate/selectById"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseData selectById(HttpServletRequest request){

        String id=request.getParameter("id");
        IRequest iRequest = createRequestContext(request);
        HmsMessageTemplate template=new HmsMessageTemplate();
        template.setId(id);
        HmsMessageTemplate result=hmsMessageTemplateService.selectByPrimaryKey(iRequest,template);

        return new ResponseData(new ArrayList<HmsMessageTemplate>(){{add(result);}});
    }

    @RequestMapping(value = {"/api/messageTemplate/save"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save(HttpServletRequest request,@RequestBody HmsMessageTemplate template){
        ResponseData responseData=new ResponseData();
        IRequest iRequest = createRequestContext(request);
        String appkey = systemConfigService.getConfigValue("sms.appKey");
        String secret = systemConfigService.getConfigValue("sms.secret");
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(appkey, secret));
        SmsClient client = new SmsClient(config);
        if(template.getId()==null) { //新增
            if("sms".equals(template.getType())) {   //短信模板
                CreateTemplateRequest createTemplateRequest = new CreateTemplateRequest();
                createTemplateRequest.setName(template.getTitle());
                createTemplateRequest.setContent(template.getContent());
                CreateTemplateResponse createResponse = client.createTemplate(createTemplateRequest);
                if (createResponse.getTemplateId() != null) {
                    template.setTemplateId(createResponse.getTemplateId());
                    hmsMessageTemplateService.insertSelective(iRequest, template);
                } else {
                    responseData.setSuccess(false);
                    responseData.setMessage("上传模板失败");
                }
            }else{
                hmsMessageTemplateService.insertSelective(iRequest, template);
            }
        }else{
            hmsMessageTemplateService.updateByPrimaryKeySelective(iRequest,template);
        }

        return responseData;
    }

    @RequestMapping(value = {"/api/messageTemplate/queryAll"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseData queryAll(HttpServletRequest request){

        IRequest iRequest = createRequestContext(request);
        List<HmsMessageTemplate> list= hmsMessageTemplateService.selectAll(iRequest);
        JSONObject result=new JSONObject();
        List<JSONObject> jPushList=new ArrayList<JSONObject>();
        List<JSONObject> smsList=new ArrayList<JSONObject>();
        List<JSONObject> emailList=new ArrayList<JSONObject>();
        JSONObject jsonObject=null;
        for(HmsMessageTemplate template:list){
          if("Y".equals(template.getEnableFlag())){
              jsonObject=new JSONObject();
              jsonObject.put("id",template.getId());
              jsonObject.put("content",template.getContent());
              jsonObject.put("title",template.getTitle());
              if("jPush".equals(template.getType())){
                  jPushList.add(jsonObject);
              }else if("sms".equals(template.getType())){
                  smsList.add(jsonObject);
              }else if("email".equals(template.getType())){
                  emailList.add(jsonObject);
              }
          }
        }
        result.put("jPush",jPushList);
        result.put("sms",smsList);
        result.put("email",emailList);

        return new ResponseData(new ArrayList<JSONObject>(){{add(result);}});
    }
}
