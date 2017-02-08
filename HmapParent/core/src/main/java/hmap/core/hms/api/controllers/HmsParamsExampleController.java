package hmap.core.hms.api.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.api.domain.HmsParamsExample;
import hmap.core.hms.api.service.IHmsParamsExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers
 * Date:2016/8/23
 * Create By:jiguang.sun@hand-china.com
 */
@Controller
@RequestMapping("/api")
public class HmsParamsExampleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HmsParamsExampleController.class);
    @Autowired
    private IHmsParamsExampleService hmsParamsExampleService;


    /*
    * 根据lineId 获取 样例
    * */
    @RequestMapping(value = "/getExample",method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getExampleById(HttpServletRequest request, @RequestBody(required = false) HmsParamsExample hmsParamsExample){

        logger.info("HmsParamsExample.lineId:{}",hmsParamsExample.getLineId());
        IRequest iRequest = createRequestContext(request);
        List<HmsParamsExample> list = hmsParamsExampleService.select(iRequest,hmsParamsExample,1,2);
        return new ResponseData(list);
    }

    /*
    * 修改或新增样例
    * */
    @RequestMapping(value = "/updateExample",method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData updateExample(HttpServletRequest request, @RequestBody(required = false) HmsParamsExample hmsParamsExample){
        logger.info("update HmsParamsExample:{}",hmsParamsExample);
        IRequest iRequest = createRequestContext(request);
        HmsParamsExample hmsParamsExampleNew  = new HmsParamsExample();
        if(hmsParamsExample.getExampleId()==null){
            logger.info("insert HmsParamsExample lineId:{}",hmsParamsExample.getLineId());
            hmsParamsExample.setExampleId(UUID.randomUUID().toString());
            hmsParamsExampleNew =  hmsParamsExampleService.insert(iRequest,hmsParamsExample);
        }else {
            logger.info("update  HmsParamsExample lineId:{}",hmsParamsExample.getLineId());
            hmsParamsExampleNew = hmsParamsExampleService.updateByPrimaryKey(iRequest,hmsParamsExample);
        }

        if(hmsParamsExampleNew != null){
            return new ResponseData();
        }else {
            return new ResponseData(false);
        }
    }

}
