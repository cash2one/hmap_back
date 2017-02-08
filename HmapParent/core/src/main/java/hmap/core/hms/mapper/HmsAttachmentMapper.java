package hmap.core.hms.mapper;


import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.domain.HmsAttachment;
import hmap.core.hms.dto.HmsAttachmentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by shanhd on 2016/10/20.
 */
public interface HmsAttachmentMapper extends Mapper<HmsAttachment> {

     int countAttachment(@Param("groupId") String groupId, @Param("type") String type);

     List<HmsAttachmentDTO> queryByGroupIdAndType(@Param("groupId") String groupId, @Param("type") String type);
}
