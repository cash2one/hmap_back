package hmap.core.hms.system.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.system.dto.HmsActiveTraceDTO;

/**
 * Created by machenike on 2016/10/21.
 */
public interface HmsActiveTraceMapper extends Mapper<HmsActiveTraceDTO> {
    HmsActiveTraceDTO selectByUserName(String userName);
}
