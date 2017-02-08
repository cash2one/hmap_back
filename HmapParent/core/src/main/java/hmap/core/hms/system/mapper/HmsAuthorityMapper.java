/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper 
 * Date:2016/9/6 0006
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.mapper;

import com.hand.hap.account.dto.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HmsAuthorityMapper {
    public List<User> selectUserByUrlAndUserName(@Param("url") String url,@Param("userName") String userName);
}
