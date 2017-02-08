/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:hstaffParent
 * Package Name:hstaff.core.staff.mapper 
 * Date:2016/7/21 0021
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.mapper;

import hmap.core.hms.contact.dto.HmsStaffAvatarDTO;

import com.hand.hap.mybatis.common.Mapper;

public interface HmsStaffAvatarMapper extends Mapper<HmsStaffAvatarDTO> {
public HmsStaffAvatarDTO selectStaffAvatarByEmpNo(String empNo);
}
