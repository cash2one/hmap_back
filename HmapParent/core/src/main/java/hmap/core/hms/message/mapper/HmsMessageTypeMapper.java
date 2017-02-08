package hmap.core.hms.message.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.message.dto.HmsMessageTypeDTO;

/**
 * Created by machenike on 2016/10/27.
 */
public interface HmsMessageTypeMapper extends Mapper<HmsMessageTypeDTO> {
    public HmsMessageTypeDTO selectByType(String type);
}
