/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.edition.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.edition.domain.HmsAppEdition;
import hmap.core.hms.edition.dto.HmapAppEditionRequestDto;
import hmap.core.hms.edition.dto.HmapAppEditionResponseDto;
import hmap.core.hms.dto.HmsAppInfoDto;
import hmap.core.hms.edition.service.IHmsAppEditionService;
import hmap.core.util.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class HmsAppEditionController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(HmsAppEditionController.class);
    @Autowired
    IHmsAppEditionService appEditionAppService;

    @RequestMapping(value="/api/getAppEditionApp",  method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData getAppEditionApp(HttpServletRequest request, @RequestBody(required = false) HmapAppEditionRequestDto hmapAppEditionRequestDto){
        HmapAppEditionResponseDto hmapAppEditionResponseDto =  appEditionAppService.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto);
        if(hmapAppEditionResponseDto==null){
            return new ResponseData(Arrays.asList());
        }
        return new ResponseData(Arrays.asList(hmapAppEditionResponseDto));
    }

    @RequestMapping(value="/api/getAppInfo",  method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData getAppInfo(HttpServletRequest request, @RequestParam(required = true)String appId,@RequestParam(required = true)String appEquipment){
        HmsAppInfoDto hmapAppInfoDto =  appEditionAppService.selectAppInfoByAppIdAndAppEquipment(appId, appEquipment);
        if (hmapAppInfoDto==null){
            return new ResponseData(Arrays.asList());
        }
        return new ResponseData(Arrays.asList(hmapAppInfoDto));
    }

    @RequestMapping(value="/api/appEditionApp", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData createAppEditionApp(HttpServletRequest request,@RequestBody(required = false) HmsAppEdition appEditionApp){
        IRequest iRequest = createRequestContext(request);
        HmsAppEdition e=appEditionAppService.insert(iRequest,appEditionApp);
        return new ResponseData(Arrays.asList(e));
    }

    @RequestMapping(value="/api/appEditionApp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getAppEditionApp(HttpServletRequest request,@PathVariable String id){
        IRequest iRequest = createRequestContext(request);
        HmsAppInfoDto e=  appEditionAppService.selectAppInfoById(id);
        return new ResponseData(Arrays.asList(e));
    }

    @RequestMapping(value="/api/appEditionApp", method = RequestMethod.GET)
    @ResponseBody
    @Timed
    public ResponseData getAllAppEditionApp(HttpServletRequest request, PageRequest pr){
        IRequest iRequest = createRequestContext(request);
        List<HmsAppInfoDto> e=  appEditionAppService.selectAppInfoList(pr.getPage(), pr.getPagesize());
        return new ResponseData(e);
    }

    @RequestMapping(value="/api/appEditionApp", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData updateAppEditionApp(HttpServletRequest request,@RequestBody(required = false) HmsAppEdition appEditionApp){
        IRequest iRequest = createRequestContext(request);
        HmsAppEdition e=appEditionAppService.updateByPrimaryKey(iRequest,appEditionApp);
        return new ResponseData(Arrays.asList(e));
    }

}
