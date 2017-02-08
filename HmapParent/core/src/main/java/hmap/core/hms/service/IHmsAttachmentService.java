package hmap.core.hms.service;


import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.domain.HmsAttachment;
import hmap.core.hms.dto.HmsAttachmentDTO;

import java.util.List;

/**
 * Created by shanhd on 2016/10/20.
 */
public interface IHmsAttachmentService extends IBaseService<HmsAttachment>,ProxySelf<IHmsAttachmentService> {

    int countAttachment(String groupId, String type);

    List<HmsAttachmentDTO> queryByGroupIdAndType(String groupId, String type, int pageCount, int pageSize);

    int batchUpdateAttachment(IRequest iRequest, List<HmsAttachment> list) throws Exception;
}
