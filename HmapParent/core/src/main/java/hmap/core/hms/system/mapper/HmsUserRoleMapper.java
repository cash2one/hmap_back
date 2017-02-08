/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper 
 * Date:2016/12/29 0029
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.mapper;

import com.hand.hap.account.dto.UserRole;
import org.apache.ibatis.annotations.Param;

public interface HmsUserRoleMapper {
    UserRole selectUserRoleByUserIdAndRoleId(@Param(value = "userId")long userId,@Param(value = "roleId")long roleId);
}
