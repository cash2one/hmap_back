package hmap.core.hms.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.domain.HmsTemplate;
import hmap.core.hms.service.IHmsTemplateService;
import hmap.core.util.StatelessRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hand on 2016/12/13.
 */
@Controller
@RequestMapping("/api")
public class HmsTemplateController {

    @Autowired
    IHmsTemplateService hmsTemplateService;

    @RequestMapping(value = "/template/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getAllTemplate(HttpServletRequest request){
        IRequest iRequest =StatelessRequestHelper.createServiceRequest(request);
        List<HmsTemplate> list =hmsTemplateService.selectAll();
        return new ResponseData(list);
    }


}
