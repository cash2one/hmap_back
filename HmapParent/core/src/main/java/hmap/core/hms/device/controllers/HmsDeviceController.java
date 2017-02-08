/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.device.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.device.domain.HmsDevice;
import hmap.core.hms.device.service.IHmsDeviceService;
import hmap.core.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class HmsDeviceController extends BaseController {
    @Autowired
    private IHmsDeviceService deviceService;
    private final Logger log = LoggerFactory.getLogger(HmsDeviceController.class);

    @RequestMapping(value = "/api/device/insert", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData insert(HttpServletRequest request,
                               @RequestBody(required = true) HmsDevice hmsDevice) {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            String user=SecurityUtils.getCurrentUserLogin();
            String object="app_userf62d8473c26c4024bfd2f2a7a80f3b96";
            hmsDevice.setDeviceUser(user);
            int e = deviceService.insertOrUpdate(hmsDevice,object.substring(0,7));
            if(e>=0){
                responseData.setSuccess(true);
                responseData.setMessage("插入成功");
            }
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }
    @RequestMapping(value = "/i/api/device/insertOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData insertOrUpdate(HttpServletRequest request,
                               @RequestBody(required = false) HmsDevice sysDeviceManagement) {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        String empNo = SecurityUtils.getCurrentUserLogin();
        sysDeviceManagement.setDeviceUser(empNo);
        sysDeviceManagement.setDeviceFlag("Y");
        try {
            HmsDevice device = deviceService.selectByIME(sysDeviceManagement.getIme());
            if(device!=null) {
                sysDeviceManagement.setDeviceId(device.getDeviceId());
                deviceService.updateByPrimaryKeySelective(iRequest,sysDeviceManagement);
            }else{
                int e = deviceService.insertDevice(sysDeviceManagement);
            }
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setMessage("插入失败");
            return responseData;
        }

    }

    @RequestMapping(value = "/api/device/query", method = RequestMethod.GET)
    @ResponseBody
    @Timed
    public ResponseData query(HttpServletRequest request,
                              @RequestParam(required = false) String deviceUser,
                              @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {


        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            List<HmsDevice> dmList = deviceService.
                    getDiviceByUser(iRequest,deviceUser,page,pagesize);
            responseData.setSuccess(true);
            responseData.setRows(dmList);
            responseData.setTotal(deviceService.selectCountByUser(deviceUser));
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }
    @RequestMapping(value = "/api/device/update", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData update(HttpServletRequest request,
                              @RequestBody(required = true) HmsDevice sysDeviceManagement) {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        List<HmsDevice> sysDeviceList = new ArrayList<HmsDevice>();
        try {
            if(deviceService.selectByDeviceId(sysDeviceManagement.getDeviceId())!=null){
                int result = deviceService.updateFlag(sysDeviceManagement.getDeviceId());
                if(result>=0){
                    HmsDevice sysDevice1 =deviceService.selectByDeviceId(sysDeviceManagement.getDeviceId());
                    sysDeviceList.add(sysDevice1);
                }
                responseData.setRows(sysDeviceList);
                responseData.setTotal(Long.valueOf(sysDeviceList.size()));
                responseData.setSuccess(true);
            }else{
                responseData.setSuccess(false);
            };

            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(false);
        }

    }

    @RequestMapping(value = "/api/device/statistic", method = RequestMethod.GET)
    @ResponseBody
    @Timed
    public ResponseData init(HttpServletRequest request) {


        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        try {
            List<HashMap> maps = deviceService.getStatistic();
            responseData.setRows(maps);
            responseData.setSuccess(true);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(false);
        }

    }

}
