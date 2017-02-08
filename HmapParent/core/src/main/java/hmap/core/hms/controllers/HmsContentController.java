package hmap.core.hms.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import hmap.core.hms.domain.*;
import hmap.core.hms.dto.HmsContentDTO;
import hmap.core.hms.system.domain.HmsSystemConfig;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.service.*;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.uploader.service.IUploaderManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hand on 2016/12/12.
 */
@Controller
@RequestMapping("/api")
public class HmsContentController  extends BaseController{
    @Autowired
    IHmsContentService hmsContentService;

    @Autowired
    IHmsTemplateService hmsTemplateService;

    @Autowired
    IHmsPublishService hmsPublishService;

    @Autowired
    IHmsPublishObjectService hmsPublishObjectService;

    @Autowired IUploaderManger uploaderManger;

    @Autowired IHmsSystemConfigService hmsSystemConfigService;

    @Autowired
    IHmsAttachmentService hmsAttachmentService;

    @Transactional
         @RequestMapping(value = {"/content/save"},method = RequestMethod.POST)
         @ResponseBody
         public ResponseData save(HttpServletRequest request,@RequestBody HmsContent dto)  {
        IRequest iRequest=createRequestContext(request);
        ResponseData responseData=new ResponseData();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String str=sdf.format(date);
        dto.setCreationDate(str);
        dto.setLastUpdateDate(str);
        dto.setContentType("10");
        dto.setCreatedBy("-1");
        dto.setLastUpdatedBy("-1");

        hmsContentService.insert(iRequest, dto);
        return responseData;
    }


    @Transactional
    @RequestMapping(value = {"/content/query"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseData query(HttpServletRequest request,@RequestBody(required=false) HmsContent dto,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)  {
        IRequest iRequest=createRequestContext(request);
        List<HmsContent> contents = hmsContentService.select(iRequest,dto,page,pagesize);
        System.out.println("      :"+contents.get(0).getContentBody());
        System.out.println(page);
        System.out.println(pagesize);
        return new ResponseData(contents);
    }


    @RequestMapping(value = {"/content/perview"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseData perview(HttpServletRequest request,@RequestBody HmsContent dto,HttpServletResponse response) throws IOException, TemplateException {
        IRequest iRequest=createRequestContext(request);
        Configuration cf = new Configuration();
        ResponseData responseData=new ResponseData();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String str=sdf.format(date);
        dto.setCreationDate(str);
        String template = dto.getTemplateId();
        HmsTemplate ht = new HmsTemplate();
        ht.setId(template);
        HmsTemplate hmsTemplate = hmsTemplateService.selectByPrimaryKey(iRequest,ht);
        String htmlKey = "ftp.htmlDir";
        HmsSystemConfig htmlConfig = hmsSystemConfigService.selectByConfigKey(htmlKey);
        String templateKey="ftp.templatePath";
        HmsSystemConfig templateConfig = hmsSystemConfigService.selectByConfigKey(templateKey);
        File fileDir = new File("c:\\templates\\");
        if(!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = "c:\\templates\\"+hmsTemplate.getTemplatePath();
        File file = new File(fileName);
        uploaderManger.getUploader().download(templateConfig.getConfigValue() + hmsTemplate.getTemplatePath(), fileName);
        cf.setDirectoryForTemplateLoading(new File("c:\\templates"));
        cf.setEncoding(Locale.getDefault(), "UTF-8");
        Map root = new HashMap();
        root.put("content", dto);
        Template t1 = cf.getTemplate(hmsTemplate.getTemplatePath());
        t1.setEncoding("UTF-8");
//      Writer out = new OutputStreamWriter(System.out);
        StringWriter stringWriter = new StringWriter();
//      Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        Writer out = new BufferedWriter(stringWriter);
        t1.process(root, out);
        String reader =  new String(stringWriter.toString());
        out.flush();
        out.close();
        System.out.println(reader);
        return new ResponseData(Arrays.asList(reader));
    }

    @Transactional
    @RequestMapping(value = {"/content/savePublish"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save(HttpServletRequest request,@RequestBody HmsContentDTO dto) throws IOException, TemplateException, ParseException {
        IRequest iRequest= createRequestContext(request);
        ResponseData responseData=new ResponseData();
        HmsContent hc = new HmsContent();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String str=sdf.format(date);
        hc.setCreationDate(str);
        hc.setLastUpdateDate(str);
        hc.setContentType("10");
        hc.setCreatedBy("-1");
        hc.setLastUpdatedBy("-1");
        hc.setContentBody(dto.getContentBody());
        hc.setContentAuthor(dto.getContentAuthor());
        hc.setContentCover(dto.getContentCover());
        hc.setContentTitle(dto.getContentTitle());
        hc.setTemplateId(dto.getTemplateId());
        hmsContentService.insert(iRequest, hc);
        List<HmsContent> HC = hmsContentService.select(iRequest,hc,1,1);
        HmsContent hmsContent = HC.get(0);
        if(hmsContent != null) {
            HmsTemplate template = new HmsTemplate();
            template.setId(hmsContent.getTemplateId());
            HmsTemplate hmsTemplate = hmsTemplateService.selectByPrimaryKey(iRequest,template);
            UploadFileDTO upd =  printHTML(hmsContent, hmsTemplate.getTemplatePath());
            HmsPublish hmsPublish = new HmsPublish();
            hmsPublish.setCreationDate(str);
            hmsPublish.setLastUpdateDate(str);
            hmsPublish.setCreatedBy("-1");
            hmsPublish.setLastUpdatedBy("-1");
            hmsPublish.setPublishDate(str);
            hmsPublish.setContentId(hmsContent.getId());
            hmsPublish.setPublishCover(hmsContent.getContentCover());
            hmsPublish.setPublishTitle(hmsContent.getContentTitle());
            hmsPublish.setPublishUrl(upd.getObjectUrl());
            hmsPublish.setPublishFile(upd.getObjectName());
            hmsPublishService.insert(iRequest, hmsPublish);
            List<HmsPublish> HP = hmsPublishService.select(iRequest,hmsPublish,1,1);
            HmsPublish hp = HP.get(0);
            HmsPublishObject hmsPublishObject = new HmsPublishObject();
            hmsPublishObject.setLastUpdateDate(str);
            hmsPublishObject.setCreationDate(str);
            hmsPublishObject.setLastUpdatedBy("-1");
            hmsPublishObject.setCreatedBy("-1");
            hmsPublishObject.setPublishId(hp.getId());
            hmsPublishObject.setUserName(dto.getUserName().toString());
            hmsPublishObjectService.insert(iRequest,hmsPublishObject);
        }
        return responseData;
    }

    public UploadFileDTO printHTML(HmsContent hmsContent,String FilePath) throws IOException, TemplateException, ParseException {
        Configuration cf = new Configuration();
        String template = hmsContent.getTemplateId();
        HmsTemplate ht = new HmsTemplate();
        ht.setId(template);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String str=sdf.format(date);
        Date time = sdf.parse(str);
        String htmlKey = "ftp.htmlDir";
        HmsSystemConfig htmlConfig = hmsSystemConfigService.selectByConfigKey(htmlKey);
        String templateKey="ftp.templatePath";
        HmsSystemConfig templateConfig = hmsSystemConfigService.selectByConfigKey(templateKey);
        File fileDir = new File("c:\\templates\\");
        if(!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = "c:\\templates\\"+FilePath;
        File file = new File(fileName);
        uploaderManger.getUploader().download(templateConfig.getConfigValue() + FilePath,fileName);
        cf.setDirectoryForTemplateLoading(new File("c:\\templates"));
        cf.setEncoding(Locale.getDefault(), "UTF-8");
        Map root = new HashMap();
        root.put("content", hmsContent);
        Template t1 = cf.getTemplate(FilePath);
        t1.setEncoding("UTF-8");
//      Writer out = new OutputStreamWriter(System.out);
        String name = time.getTime()+".html";
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:\\templates\\"+name), "UTF-8"));
        t1.process(root, out);
        out.flush();
        out.close();

        InputStream in = new FileInputStream( "c:\\templates\\" + name);
        UploadFileDTO upd =uploaderManger.getUploader().uploadFile(htmlConfig.getConfigValue(), in, name);
        return upd;
    }

}
