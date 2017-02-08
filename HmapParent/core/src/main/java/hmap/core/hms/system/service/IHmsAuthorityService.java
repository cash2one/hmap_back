/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/9/6 0006
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

import com.hand.hap.account.dto.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IHmsAuthorityService {
    public List<User> selectUserByUrlAndUserName(String url,String userName);
}
