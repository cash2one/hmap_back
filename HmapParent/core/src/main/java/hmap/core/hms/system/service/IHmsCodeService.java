package hmap.core.hms.system.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.CodeValue;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service
 * Date:2016/8/10
 * Create By:jiguang.sun@hand-china.com
 */

public interface IHmsCodeService extends ProxySelf<IHmsCodeService> {


    int insertValue(IRequest request, CodeValue codeValue);

    int updateValue(IRequest request,CodeValue codeValue);
}
