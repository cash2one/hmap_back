package hmap.core.hms.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.domain.HmsAttachment;
import hmap.core.hms.dto.HmsAttachmentDTO;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.service.IHmsAttachmentService;
import hmap.core.hms.uploader.service.IHmsUploadObjectService;
import hmap.core.util.Constant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by shanhd on 2016/10/20.
 */
@Controller
public class HmsAttachmentController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(HmsAttachmentController.class);
    @Autowired
    private IHmsAttachmentService bgAttachmentService;

    @Autowired
    IHmsUploadObjectService hmsUploadObjectService;

    @RequestMapping(value = {"/api/attachment/uploadFile"},method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData uploadFile(HttpServletRequest request,@RequestParam("file") MultipartFile[] files
            ,@RequestParam("type") String type,@RequestParam("groupId") String groupId){
        UploadFileDTO upd = null;
        ResponseData responseData = new ResponseData();
        IRequest iRequest=createRequestContext(request);
        if(Constant.ATTACHE_IMAGE.equals(type)){
            type=Constant.ATTACHE_IMAGE;
        }else{
            responseData.setSuccess(false);
            responseData.setMessage("上传文件类型错误");
            return responseData;
        }
        try {
            for(MultipartFile file:files){
                String fileName=file.getOriginalFilename();
                if(!Constant.IMG_TYPE.contains(fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase())){
                    responseData = new ResponseData(false);
                    responseData.setMessage("上传图片格式错误，支持的格式有："+Constant.IMG_TYPE);
                    break;
                }
                BufferedImage bi = ImageIO.read(file.getInputStream());
                if(bi == null){
                    responseData = new ResponseData(false);
                    responseData.setMessage("此文件不为图片文件");
                    break;
                }
                upd = this.hmsUploadObjectService.uploadFile(file, false);
                HmsAttachment attachment=new HmsAttachment();
                String id= UUID.randomUUID().toString();
                attachment.setId(id);
                attachment.setName(file.getOriginalFilename());
                attachment.setStorePath(upd.getObjectUrl());
                attachment.setType(type);
                if(!Constant.UN_GROUP_ID.equals(groupId)){
                    attachment.setGroupId(groupId);
                }
                bgAttachmentService.insertSelective(iRequest, attachment);
                HmsAttachmentDTO dto=new HmsAttachmentDTO();
                dto.setAttachmentId(id);
                dto.setAttachmentUrl(upd.getObjectUrl());
                responseData.setRows(Arrays.asList(new HmsAttachmentDTO[]{dto}));
            }
        } catch (IOException var5) {
            System.out.println("文件上传错误:" + var5.getMessage());
            responseData.setSuccess(false);
            responseData.setMessage("文件上传错误:" + var5.getMessage());
        }
        return responseData;
    }

    @RequestMapping(value = {"/api/attachment/query"},method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData query(HttpServletRequest request,@RequestBody JSONObject params){
        int pageCount=params.getInt("pageCount");
        int pageSize=params.getInt("pageSize");
        String groupId=params.getString("groupId");
        Object type=params.get("type");
        if(Constant.UN_GROUP_ID.equals(groupId)){
            groupId=null;
        }
        return new ResponseData(bgAttachmentService.queryByGroupIdAndType(groupId,
                type==null?null:type.toString(),pageCount,pageSize));
    }

    @RequestMapping(value = {"/api/attachment/modifyName"},method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData modifyName(HttpServletRequest request, @RequestBody JSONObject params){
        String id=params.getString("id");
        String newName=params.getString("newName");
        IRequest iRequest=createRequestContext(request);
        HmsAttachment attachment=new HmsAttachment();
        attachment.setId(id);
        attachment=bgAttachmentService.selectByPrimaryKey(iRequest, attachment);
        attachment.setName(newName);
        bgAttachmentService.updateByPrimaryKeySelective(iRequest,attachment);

        return new ResponseData();
    }

    @RequestMapping(value = {"/api/attachment/moveGroup"},method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData moveGroup(HttpServletRequest request, @RequestBody JSONObject params){
        try {
            JSONArray ids = params.getJSONArray("ids");
            String groupId = params.getString("groupId");
            IRequest iRequest = createRequestContext(request);
            List<HmsAttachment> list = new ArrayList<HmsAttachment>();
            for (int i = 0; i < ids.size(); i++) {
                HmsAttachment attachment = new HmsAttachment();
                attachment.setId(ids.getString(i));
                attachment = bgAttachmentService.selectByPrimaryKey(iRequest, attachment);
                attachment.setGroupId(groupId);
                list.add(attachment);
            }
            bgAttachmentService.batchUpdateAttachment(iRequest, list);
        }catch (Exception e){
            logger.error("移动分组失败");
            return new ResponseData(false);
        }

        return new ResponseData();
    }

    @RequestMapping(value = {"/api/attachment/deleteAttachment"},method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData deleteAttachment(HttpServletRequest request, @RequestBody JSONObject params){
        JSONArray ids=params.getJSONArray("ids");
        IRequest iRequest=createRequestContext(request);
        List<HmsAttachment> list=new ArrayList<HmsAttachment>();
        for (int i=0;i<ids.size();i++){
            HmsAttachment attachment=new HmsAttachment();
            attachment.setId(ids.getString(i));
            list.add(bgAttachmentService.selectByPrimaryKey(iRequest, attachment));
        }
        int result=bgAttachmentService.batchDelete(list);

        return new ResponseData(result>0);
    }
}
