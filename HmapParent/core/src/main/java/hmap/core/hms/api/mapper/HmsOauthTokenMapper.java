/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper 
 * Date:2016/10/27 0027
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.api.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.api.domain.HmsOauthToken;

public interface HmsOauthTokenMapper extends Mapper<HmsOauthToken> {
    public HmsOauthToken selectByAppId(String appId);
    public HmsOauthToken selectByAppIdAndUserName(String appId,String username);
}
