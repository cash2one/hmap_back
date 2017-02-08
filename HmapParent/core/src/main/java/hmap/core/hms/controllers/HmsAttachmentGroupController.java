package hmap.core.hms.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.domain.HmsAttachmentGroup;
import hmap.core.hms.dto.HmsAttachmentGroupDTO;
import hmap.core.hms.service.IHmsAttachmentGroupService;
import hmap.core.hms.service.IHmsAttachmentService;
import hmap.core.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanhd on 2016/10/20.
 */
@Controller
public class HmsAttachmentGroupController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(HmsAttachmentGroupController.class);

    @Autowired
    private IHmsAttachmentGroupService bgAttachmentGroupService;
    @Autowired
    private IHmsAttachmentService bgAttachmentService;

    @RequestMapping(value = {"/api/attachmentGroup/add"},method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData add(HttpServletRequest request,@RequestBody HmsAttachmentGroup group){
        IRequest iRequest=createRequestContext(request);
        group.setEnableFlag("Y");
        bgAttachmentGroupService.insertSelective(iRequest,group);
        return new ResponseData();
    }

    @RequestMapping(value = {"/api/attachmentGroup/query"},method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData query(HttpServletRequest request){
        IRequest iRequest=createRequestContext(request);
        List<HmsAttachmentGroup> groupList=bgAttachmentGroupService.selectAll();
        int count=bgAttachmentService.countAttachment(null, Constant.ATTACHE_IMAGE);
        List<HmsAttachmentGroupDTO> dtoList=new ArrayList<HmsAttachmentGroupDTO>();
        HmsAttachmentGroupDTO dto=new HmsAttachmentGroupDTO();
        dto.setId(Constant.UN_GROUP_ID);
        dto.setGroupName(Constant.UN_GROUP_NAME);
        dto.setAttachmentCount(count);
        dtoList.add(dto);
        for(HmsAttachmentGroup group:groupList){
            count=bgAttachmentService.countAttachment(group.getId(), Constant.ATTACHE_IMAGE);
            dto=new HmsAttachmentGroupDTO();
            dto.setId(group.getId());
            dto.setGroupName(group.getGroupName());
            dto.setAttachmentCount(count);
            dtoList.add(dto);
        }

        return new ResponseData(dtoList);
    }

}
