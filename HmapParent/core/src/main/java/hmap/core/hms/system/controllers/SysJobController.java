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
import com.hand.hap.job.dto.*;
import com.hand.hap.job.exception.JobException;
import com.hand.hap.job.service.IQuartzService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class SysJobController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(SysJobController.class);
    @Autowired
    IQuartzService quartzService;

    @RequestMapping(value="/createJob", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData createJob(@RequestBody JobCreateDto jobCreateDto, BindingResult result, HttpServletRequest request) throws SchedulerException, JobException, ClassNotFoundException {
        jobCreateDto.setTriggerGroup(jobCreateDto.getJobGroup());
        jobCreateDto.setTriggerName(jobCreateDto.getJobName() + "_trigger");
        this.getValidator().validate(jobCreateDto, result);
        if(result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(this.getErrorMessage(result, request));
            return rd;
        } else {
            this.quartzService.createJob(jobCreateDto);
            return new ResponseData();
        }
    }

    @RequestMapping(value="/foundJob", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData query(@RequestBody JobDetailDto example, @RequestParam(
            defaultValue = "1"
    ) int page, @RequestParam(
            defaultValue = "10"
    ) int pagesize, HttpServletRequest request) throws SchedulerException {
        return this.qj(example, page, pagesize, request);
    }

    private ResponseData qj(JobDetailDto example, int page, int pagesize, HttpServletRequest request) {
        IRequest requestCtx = this.createRequestContext(request);
        return new ResponseData(this.quartzService.getJobInfoDetails(requestCtx, example, page, pagesize));
    }

    @RequestMapping(value="/pausejob", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData pauseJobs(@RequestBody List<JobDetailDto> list) throws SchedulerException {
        this.quartzService.pauseJobs(list);
        return new ResponseData();
    }

    @RequestMapping(value="/resumejob", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData resumeJobs(@RequestBody List<JobDetailDto> list) throws SchedulerException {
        this.quartzService.resumeJobs(list);
        return new ResponseData();
    }

    @RequestMapping(value="/deletejob", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData deleteJobs(@RequestBody List<JobDetailDto> list) throws SchedulerException {
        this.quartzService.deleteJobs(list);
        return new ResponseData();
    }




    @RequestMapping(value="/pausetrigger", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData pauseTrigger(@RequestBody List<TriggerDto> list) throws SchedulerException {
        this.quartzService.pauseTriggers(list);
        return new ResponseData();
    }

    @RequestMapping(value="/resumetrigger", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData resumeTrigger(@RequestBody List<TriggerDto> list) throws SchedulerException {
        this.quartzService.resumeTriggers(list);
        return new ResponseData();
    }

    @RequestMapping(value="/query", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData queryJobs(@ModelAttribute JobDetailDto example, @RequestParam(
            defaultValue = "1"
    ) int page, @RequestParam(
            defaultValue = "10"
    ) int pagesize, HttpServletRequest request) throws SchedulerException {
        return this.qj(example, page, pagesize, request);
    }

    @RequestMapping(value="/trigger", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData queryTrigger(@RequestParam(
            required = true
    ) String triggerName, @RequestParam(
            required = true
    ) String triggerGroup, @RequestParam(
            required = true
    ) String triggerType) throws SchedulerException {
        return "CRON".equalsIgnoreCase(triggerType)?new ResponseData(Arrays.asList(new CronTriggerDto[]{this.quartzService.getCronTrigger(triggerName, triggerGroup)})):("SIMPLE".equalsIgnoreCase(triggerType)?new ResponseData(Arrays.asList(new SimpleTriggerDto[]{this.quartzService.getSimpleTrigger(triggerName, triggerGroup)})):new ResponseData());
    }

    @RequestMapping(value="/trigger/query", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData queryTriggers(@ModelAttribute TriggerDto example, @RequestParam(
            defaultValue = "1"
    ) int page, @RequestParam(
            defaultValue = "10"
    ) int pagesize, HttpServletRequest request) throws SchedulerException {
        IRequest requestCtx = this.createRequestContext(request);
        return new ResponseData(this.quartzService.getTriggers(requestCtx, example, page, pagesize));
    }

    @RequestMapping(value="/scheduler/query", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData querySchedulers(@ModelAttribute SchedulerDto example, @RequestParam(
            defaultValue = "1"
    ) int page, @RequestParam(
            defaultValue = "10"
    ) int pagesize) throws SchedulerException {
        return new ResponseData(this.quartzService.selectSchedulers(example, page, pagesize));
    }

    @RequestMapping(value="/scheduler/info", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData schedulerInformation() throws SchedulerException {
        Map infoMap = this.quartzService.schedulerInformation();
        return new ResponseData(Arrays.asList(infoMap));
    }

    @RequestMapping(value="/scheduler/start", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData startScheduler() throws SchedulerException {
        return new ResponseData(Arrays.asList(this.quartzService.start()));
    }

    @RequestMapping(value="/scheduler/standby", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData standbyScheduler() throws SchedulerException {
        return new ResponseData(Arrays.asList(this.quartzService.standby()));
    }

    @RequestMapping(value="/scheduler/pauseall", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData schedulerPauseAll() throws SchedulerException {
        return new ResponseData(Arrays.asList(this.quartzService.pauseAll()));
    }

    @RequestMapping(value="/scheduler/resumeall", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData schedulerResumeAll() throws SchedulerException {
        return new ResponseData(Arrays.asList(this.quartzService.resumeAll()));
    }

}
