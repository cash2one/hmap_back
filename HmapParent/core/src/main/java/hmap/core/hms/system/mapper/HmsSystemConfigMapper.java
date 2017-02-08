/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper Date:2016/9/22 0022 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.mapper;

import com.hand.hap.mybatis.common.Mapper;

import hmap.core.hms.system.domain.HmsSystemConfig;

import java.util.List;

public interface HmsSystemConfigMapper extends Mapper<HmsSystemConfig> {
  List<HmsSystemConfig> selectByConfigKey(String configKey);
}
