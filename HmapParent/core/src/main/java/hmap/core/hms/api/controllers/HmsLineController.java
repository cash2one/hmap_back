package hmap.core.hms.api.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.cache.impl.ApiCache;
import hmap.core.hms.api.domain.HmsInterfaceLine;
import hmap.core.hms.api.dto.LineAndLineTlDTO;
import hmap.core.hms.api.mapper.HmsLineMapper;
import hmap.core.hms.api.service.IHmsLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author jiguang.sun@hand-china.com
 * @version 2016/7/26.
 */
@Controller
@RequestMapping("/api")
public class HmsLineController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(HmsLineController.class);

    @Autowired
    private IHmsLineService lineService;

    @Autowired
    private ApiCache apiCache;
    @Autowired
    private HmsLineMapper hmsLineMapper;


    /*
    * get line and lineTl by lineId and language
    * */
    @RequestMapping(value = "/queryLine", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getLineList(HttpServletRequest request, @RequestBody(required = false) LineAndLineTlDTO lineAndLineTlDTO) {
        logger.info("query line by LineAndLineTlDTO  lineId:{}", lineAndLineTlDTO.getLineId());

        //TODO language根据登录获取
        lineAndLineTlDTO.setLang("zh_CN");

        return new ResponseData(lineService.getLineAndLineTl(lineAndLineTlDTO));
    }


    /*
    * 新增一个接口
    * */
    @RequestMapping(value = "/insertLine", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData insertLine(HttpServletRequest request, @RequestBody(required = false) HmsInterfaceLine hmsInterfaceLine) {
        logger.info("add line by LineAndLineTlDTO  headerId:{}", hmsInterfaceLine.getHeaderId());
        IRequest iRequest = createRequestContext(request);
        //TODO 从页面获取不到语言
        iRequest.setLocale("zh_CN");

        hmsInterfaceLine.setLineId(UUID.randomUUID().toString());
        hmsInterfaceLine.setLineDescription(hmsInterfaceLine.getLineName());
        int result = lineService.insertLine(iRequest, hmsInterfaceLine);


        if (result > 0) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }

    }


    /*
    * 更新
    * */
    @RequestMapping(value = "/updateLine", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData updateLine(HttpServletRequest request, @RequestBody(required = false) HmsInterfaceLine hmsInterfaceLine) {
        logger.info("update line by hmsInterfaceLine  lineId:{}", hmsInterfaceLine.getLineId());


        IRequest iRequest = createRequestContext(request);
        //TODO 从页面获取不到语言
        iRequest.setLocale("zh_CN");
        hmsInterfaceLine.setLineDescription(hmsInterfaceLine.getLineName());
        int result = lineService.updateLine(iRequest, hmsInterfaceLine);


        if (result > 0) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }

    }

    /*
    * 根据headerId获取lines
    * */
    @RequestMapping(value = "/getLinesByHeaderId", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData getLinesByHeaderId(HttpServletRequest request, @RequestBody(required = false) LineAndLineTlDTO lineAndLineTlDTO) {

        //TODO language根据登录获取
        lineAndLineTlDTO.setLang("zh_CN");
        logger.info("LineAndLineTlDTO params:{} ,{},{}", lineAndLineTlDTO.getHeaderId(), lineAndLineTlDTO.getPage(), lineAndLineTlDTO.getPagesize());

        return new ResponseData(lineService.getLinesByHeaderId(lineAndLineTlDTO));
    }


}
