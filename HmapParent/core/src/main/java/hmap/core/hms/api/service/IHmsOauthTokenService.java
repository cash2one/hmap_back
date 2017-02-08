/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/10/27 0027
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.api.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.api.domain.HmsOauthToken;

import java.util.Map;

public interface IHmsOauthTokenService
    extends IBaseService<HmsOauthToken>,ProxySelf<IHmsOauthTokenService> {
    public HmsOauthToken getAccessToken(Map<String, String> oauthConfig,String isLogin);
}
