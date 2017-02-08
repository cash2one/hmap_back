package hmap.core.hms.edition.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import hmap.core.hms.edition.domain.HmsAppEditionLine;
import hmap.core.hms.edition.service.IHmsAppEditionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by enlline on 12/30/16.
 */
@Controller
@RequestMapping("/api/getAppEditionLine")
public class HmsAppEditionLineController extends BaseController {

    @Autowired
    private IHmsAppEditionLineService iHmsAppeditionLineService;

    @RequestMapping(value="/insert",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseData insert(HttpServletRequest request, @RequestBody(required = false) HmsAppEditionLine appEditionAppLine){
        IRequest iRequest = createRequestContext(request);
        HmsAppEditionLine tmpAppEditionAppLine = iHmsAppeditionLineService.insertAppEditionAppLine(iRequest, appEditionAppLine);
        return new ResponseData(Arrays.asList(tmpAppEditionAppLine));
    }

    @RequestMapping(value="/update",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody(required = false) HmsAppEditionLine appEditionAppLine){
        IRequest iRequest = createRequestContext(request);
        HmsAppEditionLine tmpAppEditionAppLine = iHmsAppeditionLineService.updateAppEditionAppLine(iRequest, appEditionAppLine);
        return new ResponseData(Arrays.asList(tmpAppEditionAppLine));
    }

    @RequestMapping(value="/queryByAppEditionId",  method = RequestMethod.GET)
    @ResponseBody
    public ResponseData queryByAppId(HttpServletRequest request, @RequestParam(required = true) String  appEditionId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize){
        IRequest iRequest = createRequestContext(request);
        List<HmsAppEditionLine> AppEditionAppLineList = iHmsAppeditionLineService.selectByAppId(appEditionId, page, pagesize);
        return new ResponseData(Arrays.asList(AppEditionAppLineList));
    }

    @RequestMapping(value="/queryAppEditionLineById",  method = RequestMethod.GET)
    @ResponseBody
    public ResponseData queryAppEditionLineById(HttpServletRequest request, @RequestParam(required = true) String  id){
        IRequest iRequest = createRequestContext(request);
        HmsAppEditionLine HmsAppEditionLine = new HmsAppEditionLine();
        HmsAppEditionLine.setId(id);
        HmsAppEditionLine tmpAppEditionAppLine = iHmsAppeditionLineService.selectByPrimaryKey(iRequest, HmsAppEditionLine);
        return new ResponseData(Arrays.asList(tmpAppEditionAppLine));
    }
    @RequestMapping(value="/deleteAppEditionLineById",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteAppEditionLineById(HttpServletRequest request, @RequestBody(required = false) HmsAppEditionLine appEditionAppLine){
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        int line = iHmsAppeditionLineService.deleteByPrimaryKey(appEditionAppLine);
        if(line==0){
            responseData.setSuccess(false);
        }else{
            responseData.setSuccess(true);
        }
        return responseData;
    }
}
