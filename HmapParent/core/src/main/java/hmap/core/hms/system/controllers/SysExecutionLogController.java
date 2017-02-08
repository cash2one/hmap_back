/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.system.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.job.dto.JobRunningInfoDto;
import com.hand.hap.job.service.IJobRunningInfoService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
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
public class SysExecutionLogController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(SysExecutionLogController.class);
    @Autowired
    IJobRunningInfoService jobRunningInfoService;

    @RequestMapping(value="/ExecutionLog", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData getExecutionLog(HttpServletRequest request, @RequestParam(
            defaultValue = "1"
    ) int page, @RequestParam(
            defaultValue = "10"
    ) int pagesize,@RequestBody(required = false) JobRunningInfoDto dto){
        IRequest iRequest = createRequestContext(request);
        System.out.println("getPage:"+page+"getPagesize:"+pagesize);
        List<JobRunningInfoDto> e=  jobRunningInfoService.queryJobRunningInfo(iRequest, dto, page, pagesize);
        return new ResponseData(e);
    }
}
