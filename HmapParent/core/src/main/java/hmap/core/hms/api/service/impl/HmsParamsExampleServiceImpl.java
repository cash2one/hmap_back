package hmap.core.hms.api.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.api.domain.HmsParamsExample;
import hmap.core.hms.api.mapper.HmsParamsExampleMapper;
import hmap.core.hms.api.service.IHmsParamsExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl
 * Date:2016/8/23
 * Create By:jiguang.sun@hand-china.com
 */
@Service
@Transactional
public class HmsParamsExampleServiceImpl extends BaseServiceImpl<HmsParamsExample> implements IHmsParamsExampleService {

    @Autowired
    private HmsParamsExampleMapper hmsParamsExampleMapper;
}
