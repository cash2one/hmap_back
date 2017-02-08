package hmap.core.hms.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.domain.HmsAttachment;
import hmap.core.hms.dto.HmsAttachmentDTO;
import hmap.core.hms.mapper.HmsAttachmentMapper;
import hmap.core.hms.service.IHmsAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shanhd on 2016/10/20.
 */
@Service
public class HmsAttachmentServiceImpl extends BaseServiceImpl<HmsAttachment> implements IHmsAttachmentService {

    @Autowired
    private HmsAttachmentMapper hmsAttachmentMapper;

    @Override
    public int countAttachment(String groupId, String type) {
        return hmsAttachmentMapper.countAttachment(groupId,type);
    }

    @Override
    public List<HmsAttachmentDTO> queryByGroupIdAndType(String groupId, String type,int pageCount,int pageSize) {
        PageHelper.startPage(pageCount, pageSize);
        return hmsAttachmentMapper.queryByGroupIdAndType(groupId,type);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int batchUpdateAttachment(IRequest iRequest, List<HmsAttachment> list) throws Exception {
        HmsAttachment result=null;
        for(HmsAttachment attachment:list){
            result=self().updateByPrimaryKeySelective(iRequest, attachment);
            if(result==null){
                throw new Exception("更新失败");
            }
        }
        return 1;
    }
}
