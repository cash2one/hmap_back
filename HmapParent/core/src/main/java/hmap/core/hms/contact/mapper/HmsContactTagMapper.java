/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper 
 * Date:2016/11/11 0011
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.contact.domain.HmsContactTag;

import java.util.List;

public interface HmsContactTagMapper extends Mapper<HmsContactTag> {
    List<HmsContactTag> selectAllTags(String userName);
}
