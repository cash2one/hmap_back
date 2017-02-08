package hmap.core.hms.system.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.Code;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import hmap.core.hms.system.service.IHmsCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers
 * Date:2016/8/9
 * Create By:jiguang.sun@hand-china.com
 */

@Controller
@RequestMapping("/api")
public class HmsCodeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(HmsCodeController.class);

    @Autowired
    private ICodeService codeService;
    @Autowired
    private IHmsCodeService hmsCodeService;

    /**
     * 获取快速编码对象.
     *
     * @param code     Code
     * @param page     起始页
     * @param pagesize 分页大小
     * @param request  HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/code/query", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getCodes(HttpServletRequest request, @RequestBody(required = false) Code code, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        System.out.println("code is : "+code);
        logger.info("params:{}", code);
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");

        List<Code> list = codeService.selectCodes(requestContext, code, page, pagesize);
        logger.info("listCode:{}", list);

        return new ResponseData(list);
    }


    /*
    * 新增code
    * */
    @RequestMapping(value = "/code/insert", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData insertCode(HttpServletRequest request, @RequestBody(required = false) Code code) {
        Code rCode = codeService.createCode(code);

        boolean flag = false;
        if (rCode != null) {
            flag = true;
        }
        return new ResponseData(flag);
    }

    /*
    * 更新code
    * */
    @RequestMapping(value = "/code/update", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData updateCode(HttpServletRequest request, @RequestBody(required = false) Code code) {
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");
        System.out.println("code=======:" + code);
        Code rCode = codeService.updateCode(code);

        boolean flag = false;
        if (rCode != null) {
            flag = true;
        }
        return new ResponseData(flag);
    }

    /*
    * 删除code
    * */
    @RequestMapping(value = "/code/delete", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData deleteCode(HttpServletRequest request,@RequestBody(required = false) Code code){
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");
        List<Code> list = new ArrayList<Code>();
        list.add(code);
        boolean flag = codeService.batchDelete(requestContext,list);
        if(flag){
            return new ResponseData();
        }else {
            return new ResponseData(false);
        }

    }


   /* *//*
    * 根据code获取value
    * *//*
    @RequestMapping(value = "/codeValue/getValuesByCode", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getValueByCodeName(HttpServletRequest request, @RequestBody(required = false) Code code) {
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");
        List<CodeValue> list = codeService.selectCodeValuesByCodeName(requestContext, code.getCode());
        return new ResponseData(list);
    }*/

    /*
    *查询codeValue
    * */
    @RequestMapping(value = "/codeValue/getValues", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getValues(HttpServletRequest request, @RequestBody(required = false) CodeValue codeValue) {
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");
        List<CodeValue> list = codeService.selectCodeValues(requestContext, codeValue);

        return new ResponseData(list);

    }

    /*
    * 修改value
    * */
    @RequestMapping(value = "/codeValue/update", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData updateValue(HttpServletRequest request, @RequestBody(required = false) CodeValue codeValue) {
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");
        System.out.println("codeValue=====:" + codeValue);
        int result = hmsCodeService.updateValue(requestContext, codeValue);

        boolean flag = false;
        if (result > 0) {
            flag = true;
        }
        return new ResponseData(flag);
    }

    /*
    * 新增value
    * */
    @RequestMapping(value = "/codeValue/insert", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData insertValue(HttpServletRequest request, @RequestBody(required = false) CodeValue codeValue) {
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");
        int result = hmsCodeService.insertValue(requestContext, codeValue);

        boolean flag = false;
        if (result > 0) {
            flag = true;
        }
        return new ResponseData(flag);
    }

    /*
    * 删除codeValue
    * */
    @RequestMapping(value = "/codeValue/delete")
    @ResponseBody
    @Timed
    public ResponseData deleteCodeValue(HttpServletRequest request,@RequestBody(required = false) CodeValue codeValue){
        IRequest requestContext = createRequestContext(request);
        //TODO 语言
        requestContext.setLocale("zh_CN");
        List<CodeValue> list = new ArrayList<CodeValue> ();
        list.add(codeValue);
        boolean flag = codeService.batchDeleteValues(requestContext,list);

        if(flag){
            return new ResponseData();
        }else {
            return new ResponseData(false);
        }

    }


}
