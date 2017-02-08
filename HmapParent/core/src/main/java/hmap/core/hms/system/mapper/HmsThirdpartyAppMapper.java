/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper 
 * Date:2016/7/31 0031
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.system.domain.HmsThirdpartyApp;

public interface HmsThirdpartyAppMapper extends Mapper<HmsThirdpartyApp> {
    public HmsThirdpartyApp selectById(String id);
    public HmsThirdpartyApp selectByCode(String code);
    public HmsThirdpartyApp selectByAppKey(String appKey);
}
