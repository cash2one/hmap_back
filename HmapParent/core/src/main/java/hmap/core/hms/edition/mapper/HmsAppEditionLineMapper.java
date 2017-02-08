/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper 
 * Date:2016/7/31 0031
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.edition.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.edition.domain.HmsAppEditionLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HmsAppEditionLineMapper extends Mapper<HmsAppEditionLine> {

    public List<HmsAppEditionLine> selectByAppId(@Param("appEditionId")String appEditionId);

    public int updateEdition(@Param("appEditionId")String appEditionId,@Param("isLatestEdition")String isLatestEdition,
                             @Param("isMinimumEdition")String isMinimumEdition);


}
