/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/11/11 0011
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.contact.domain.HmsContactTagMember;

public interface IHmsContactTagMemberService extends IBaseService<HmsContactTagMember>,
    ProxySelf<IHmsContactTagMemberService> {
}
