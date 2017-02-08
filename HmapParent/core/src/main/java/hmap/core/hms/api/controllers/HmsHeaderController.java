package hmap.core.hms.api.controllers;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import hmap.core.util.StatelessRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;

import hmap.core.cache.impl.ApiCache;
import hmap.core.hms.api.domain.HmsInterfaceHeader;
import hmap.core.hms.api.dto.HeaderAndHeaderTlDTO;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.service.IHmsHeaderService;

import hmap.core.util.PageRequest;

/**
 * @author jiguang.sun@hand-china.com
 * @version 2016/7/21
 */

@Controller
@RequestMapping("/api")
public class HmsHeaderController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(HmsHeaderController.class);

    @Autowired
    private IHmsHeaderService headerService;

    @Autowired
    private ApiCache apiCache;

    @Autowired
    private ICodeService codeService;


    /**
     * 获取所有的系统路径
     */
    @RequestMapping(value = "/queryAllHeader", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getHeaderList(HttpServletRequest request, @RequestBody(required = false) HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {

        logger.info("get HeaderAndHeaderTlDTO  ={} ", headerAndHeaderTlDTO.getInterfaceCode());
        logger.info("get HeaderAndHeaderTlDTO  page ={} ", headerAndHeaderTlDTO.getPagesize());
        //TODO 用户进入页面根据选择语言查询数据
        headerAndHeaderTlDTO.setLang("zh_CN");
        List<HeaderAndHeaderTlDTO> list = headerService.getAllHeader(headerAndHeaderTlDTO);
        return new ResponseData(list);

    }

    /**
     * 查询所有系统类型
     *
     * @param request
     * @return
     * @throws UserException
     */
    @RequestMapping(value = "/queryAllHeader", method = RequestMethod.GET)
    @ResponseBody
    @Timed
    public ResponseData getAllSystemType(HttpServletRequest request) throws UserException {
        IRequest iRequest = createRequestContext(request);

        //TODO 语言
        iRequest.setLocale("zh_CN");
        CodeValue codeValue = new CodeValue();
        Long id = Long.parseLong("135");
        codeValue.setCodeId(id);
        List<CodeValue> list = codeService.selectCodeValues(iRequest, codeValue);

        return new ResponseData(list);
    }

    /*
    * 新增 HmsInterfaceHeader
    * */
    @RequestMapping(value = "/addHeader", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData addHeader(HttpServletRequest request, @RequestBody(required = false) HmsInterfaceHeader hmsInterfaceHeader) {
        logger.info("add HmsInterfaceHeader and HmsInterfaceHeaderTl:{} ", hmsInterfaceHeader.getInterfaceCode());
        IRequest iRequest = createRequestContext(request);
        //TODO 从页面获取不到语言
        iRequest.setLocale("zh_CN");
        hmsInterfaceHeader.setHeaderId(UUID.randomUUID().toString());
        hmsInterfaceHeader.setDescription(hmsInterfaceHeader.getName());
        HmsInterfaceHeader hmsInterfaceHeaderNew = headerService.insert(iRequest, hmsInterfaceHeader);

        if (hmsInterfaceHeaderNew != null) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }

    }

    /*
    * 更新 HeaderAndHeaderTlDTO
    * */
    @RequestMapping(value = "/updateHeader", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData updateHeader(HttpServletRequest request, @RequestBody(required = false) HmsInterfaceHeader hmsInterfaceHeader) {
        logger.info("update HmsInterfaceHeader  headerId:{} ", hmsInterfaceHeader.getHeaderId());
        IRequest iRequest = createRequestContext(request);
        //TODO 从页面获取不到语言
        iRequest.setLocale("zh_CN");
        hmsInterfaceHeader.setDescription(hmsInterfaceHeader.getName());

        int result = headerService.updateHeader(iRequest, hmsInterfaceHeader);

        if (result > 0) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }


    }

    /*
    * 根据headerId 查询 header and line
    * */
    @RequestMapping(value = "/getHeaderAndLine", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getHeaderAndLine(HttpServletRequest request, @RequestBody(required = false) HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {

        logger.info("getHeaderAndLine headerId={}", headerAndHeaderTlDTO.getHeaderId());
        headerAndHeaderTlDTO.setLang("zh_CN");

        return new ResponseData(headerService.getHeaderAndLineList(headerAndHeaderTlDTO));

    }

    /*
    * 根据headerId获取header
    * */
    @RequestMapping(value = "/getHeaderByHeaderId", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getHeaderByHeaderId(HttpServletRequest request, @RequestBody(required = false) HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {
        logger.info("getHeader by headerId:{}", headerAndHeaderTlDTO.getHeaderId());
        headerAndHeaderTlDTO.setLang("zh_CN");
        return new ResponseData(headerService.getHeaderByHeaderId(headerAndHeaderTlDTO));
    }


    /*
    * 根据lineId获取headerAndLine
    * */
    @RequestMapping(value = "/getHeaderAndLineByLineId", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getHeaderAndLineByLineId(HttpServletRequest request, @RequestBody(required = false) HeaderAndLineDTO headerAndLineDTO) {
        logger.info("getHeaderAndLineByLineId lineId:{}", headerAndLineDTO.getLineId());
        HeaderAndLineDTO headerAndLineDTO1 = headerService.getHeaderAndLineByLineId(headerAndLineDTO);

        return new ResponseData(Arrays.asList(headerAndLineDTO1));
    }

    /*
    * 所有有效的请求
    * */
    @RequestMapping(value = "/getAllHeaderAndLine", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getAllHeaderAndLine(HttpServletRequest request, @RequestBody(required = false) PageRequest pageRequest) {

        logger.info("page:{} ,pagesize:{}", pageRequest.getPage(), pageRequest.getPagesize());
        return new ResponseData(headerService.getAllHeaderAndLine(pageRequest));
    }
    /*
    * 同步Mock配置
    * */
    @RequestMapping(value = "/syncMockConfig", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData syncMockConfig(HttpServletRequest request, @RequestBody(required = false) HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {
       IRequest iRequest= StatelessRequestHelper.createServiceRequest(request);
       boolean result= headerService.syncMockConfig(iRequest,headerAndHeaderTlDTO);
        if (result) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }
    }

}
