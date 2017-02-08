/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/11/19 0019
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.service;

import hmap.core.hms.permission.dto.PermissionDataDTO;

import java.util.List;

public interface IContactListService {
    List<PermissionDataDTO> search(String key);
}
