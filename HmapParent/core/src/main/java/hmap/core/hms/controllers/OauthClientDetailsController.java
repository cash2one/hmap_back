package hmap.core.hms.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.appcenter.controllers.HmsAppAuthController;
import hmap.core.hms.domain.OauthClientDetails;
import hmap.core.hms.service.IOauthClientDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xincai.zhang on 2016/8/15.
 */
@Controller
@RequestMapping("/api")
public class OauthClientDetailsController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(HmsAppAuthController.class);
    @Autowired
    IOauthClientDetailsService iOauthClientDetailsService;
    @RequestMapping(value = "/oauthclientdetails/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getAppAuth(HttpServletRequest request, @RequestBody(required = false) OauthClientDetails oauthClientDetails, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        System.out.println("oauthClientDetails is:" + oauthClientDetails);
        return new ResponseData(iOauthClientDetailsService.select(requestContext, oauthClientDetails, page, pagesize));
    }
}
