/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.permission.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.permission.domain.HmsApplicationPermission;
import hmap.core.hms.permission.service.IHmsApplicationPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api")
public class HmsApplicationPermissionController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(HmsApplicationPermissionController.class);
    @Autowired
    IHmsApplicationPermissionService iHmsApplicationPermissionService;

    @RequestMapping(value = "/applicationPermission/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData saveAppeditionApp(HttpServletRequest request, @RequestBody(required = false) List<HmsApplicationPermission> ApplicationPermissions,@RequestParam(required = true) String dataId) {
        IRequest iRequest = createRequestContext(request);
        iHmsApplicationPermissionService.saveAll(iRequest,ApplicationPermissions,dataId);
        return new ResponseData();
    }

    @RequestMapping(value = "/applicationPermission/query/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getAppeditionApp(HttpServletRequest request, @PathVariable String id) {
        List<HmsApplicationPermission> e = iHmsApplicationPermissionService.foungData(id);
        System.out.println(e.size());
        return new ResponseData(e);
    }
}
