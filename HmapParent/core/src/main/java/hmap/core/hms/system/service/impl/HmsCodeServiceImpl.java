package hmap.core.hms.system.service.impl;

import com.hand.hap.cache.impl.SysCodeCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.mapper.CodeValueMapper;
import hmap.core.hms.system.service.IHmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl
 * Date:2016/8/10
 * Create By:jiguang.sun@hand-china.com
 */
@Service
@Transactional
public class HmsCodeServiceImpl implements IHmsCodeService {


    @Autowired
    private CodeValueMapper codeValueMapper;
    @Autowired
    private SysCodeCache codeCache;

    @Override
    public int insertValue(IRequest request, CodeValue codeValue) {

        int row = codeValueMapper.insertSelective(codeValue);
        codeCache.reload(codeValue.getCodeId());
        return  row;
    }

    @Override
    public int updateValue(IRequest request,CodeValue codeValue) {
        int row = codeValueMapper.updateByPrimaryKeySelective(codeValue);
        codeCache.reload(codeValue.getCodeId());
        return row;
    }

}
