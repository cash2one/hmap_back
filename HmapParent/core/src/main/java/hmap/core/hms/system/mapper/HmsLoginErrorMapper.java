/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper Date:2016/11/8 0008 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.system.domain.HmsLoginError;

public interface HmsLoginErrorMapper extends Mapper<HmsLoginError> {
  HmsLoginError selectByUserIp(String userIp);
}
