/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl 
 * Date:2016/11/8 0008
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.system.domain.HmsLoginError;
import hmap.core.hms.system.mapper.HmsLoginErrorMapper;
import hmap.core.hms.system.service.IHmsLoginErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HmsLoginErrorServiceImpl extends BaseServiceImpl<HmsLoginError>
    implements IHmsLoginErrorService {
    @Autowired
    private HmsLoginErrorMapper hmsLoginErrorMapper;
    @Override public HmsLoginError selectByUserIp(String userIp) {
        return hmsLoginErrorMapper.selectByUserIp(userIp);
    }
}
