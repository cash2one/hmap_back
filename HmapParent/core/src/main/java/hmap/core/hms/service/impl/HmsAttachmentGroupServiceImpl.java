package hmap.core.hms.service.impl;


import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.domain.HmsAttachmentGroup;
import hmap.core.hms.mapper.HmsAttachmentGroupMapper;
import hmap.core.hms.service.IHmsAttachmentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shanhd on 2016/10/20.
 */
@Service
public class HmsAttachmentGroupServiceImpl extends BaseServiceImpl<HmsAttachmentGroup> implements IHmsAttachmentGroupService {

    @Autowired
    private HmsAttachmentGroupMapper hmsAttachmentGroupMapper;
}
