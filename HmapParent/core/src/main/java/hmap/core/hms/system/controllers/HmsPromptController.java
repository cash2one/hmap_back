package hmap.core.hms.system.controllers;

import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.Language;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ILanguageService;
import com.hand.hap.system.service.IPromptService;
import hmap.core.hms.system.domain.SysPrompt;
import hmap.core.hms.system.service.ISysPromptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import com.codahale.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Koma.Tshu on 2016/8/11.
 */
@Controller
@RequestMapping("/api")
public class HmsPromptController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(HmsPromptController.class);
    @Autowired
    private ISysPromptService promptService;
    @Autowired
    private IPromptService basePromptService;
    @Autowired
    private ILanguageService languageService;

    /**
     * 查询全部描述方法
     * @param request
     * @return
     * @throws UserException
     */
    @RequestMapping(value = "/prompt/queryPrompts")
    @ResponseBody
    public ResponseData getAllPrompts(HttpServletRequest request, @RequestBody(required = false)SysPrompt sysPrompt, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize)
            throws UserException {
        log.info("page:"+page);
        log.info("pagesize:"+pageSize);
        log.info("sysPrompt.getPromptCode:" + sysPrompt.getPromptCode());
        log.info("sysPrompt.getAppDescription:" + sysPrompt.getDescription());
        log.info("sysPrompt.getLang:" + sysPrompt.getLang());
        List<SysPrompt> list = promptService.selectAllPrompts(sysPrompt,page,pageSize);
        log.info("list.size():" + list.size());
        return new ResponseData(list);
    }

    /**
     * 描述新增方法
     * @param request
     * @param prompt
     * @return
     */
    @RequestMapping(value = "/prompt/savePrompt",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData createPrompt(HttpServletRequest request,
                                            @RequestBody(required = false) SysPrompt prompt) {
        IRequest iRequest = createRequestContext(request);
        SysPrompt e = promptService.insertSelective(iRequest, prompt);
        return new ResponseData(Arrays.asList(e));
    }

    /**
     * 描述编辑方法
     * @param request
     * @param prompt
     * @return
     */
    @RequestMapping(value = "/prompt/updatePrompt",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData updatePrompt(HttpServletRequest request,
                                            @RequestBody(required = false) SysPrompt prompt) {
        log.info("update prompt:" + prompt);
        IRequest iRequest = createRequestContext(request);
        SysPrompt e = promptService.updateByPrimaryKeySelective(iRequest, prompt);
        return new ResponseData(Arrays.asList(e));
    }

    /**
     * 根据描述id查询描述对象
     * @param request
     * @param prompt
     * @return
     * @throws UserException
     */
    @RequestMapping(value = "/prompt/findByPromptId")
    @ResponseBody
    public ResponseData findByPromptId(HttpServletRequest request, @RequestBody(required = false) SysPrompt prompt)
            throws UserException {
        IRequest iRequest = createRequestContext(request);
        SysPrompt e = promptService.selectByPromptId(prompt.getPromptId());
        return new ResponseData(Arrays.asList(e));
    }

    /**
     * 查询语言
     * @param request
     * @param example
     * @param page
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "/prompt/queryLang")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, Language example,
                       @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        log.info("queryLang page:" + page);
        log.info("queryLang pageSize:" + pagesize);
        log.info("queryLang example:" + example);
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(languageService.select(iRequest, null, page, pagesize));
    }
}
