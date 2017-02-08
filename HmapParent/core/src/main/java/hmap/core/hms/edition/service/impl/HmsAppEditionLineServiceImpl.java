/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.edition.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.edition.domain.HmsAppEditionLine;
import hmap.core.hms.edition.mapper.HmsAppEditionLineMapper;
import hmap.core.hms.edition.service.IHmsAppEditionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class HmsAppEditionLineServiceImpl extends BaseServiceImpl<HmsAppEditionLine>
    implements IHmsAppEditionLineService {
    @Autowired
    private HmsAppEditionLineMapper hmsAppeditionLineMapper;
    @Override
    public List<HmsAppEditionLine> selectByAppId(String appId, int page, int pagesize) {
        PageHelper.startPage(page,pagesize);
        return hmsAppeditionLineMapper.selectByAppId(appId);
    }

    @Override
    public HmsAppEditionLine updateAppEditionAppLine(IRequest iRequest,HmsAppEditionLine appEditionAppLine) {
        if(appEditionAppLine.getIsMinimumEdition().equals("Y")||appEditionAppLine.getIsLatestEdition().equals("Y")){
            hmsAppeditionLineMapper.updateEdition(appEditionAppLine.getAppEditionId(),appEditionAppLine.getIsLatestEdition(),appEditionAppLine.getIsMinimumEdition());
        }
        return this.updateByPrimaryKey(iRequest,appEditionAppLine);
    }

    @Override
    public HmsAppEditionLine insertAppEditionAppLine(IRequest iRequest, HmsAppEditionLine appEditionAppLine) {
        if(appEditionAppLine.getIsMinimumEdition().equals("Y")||appEditionAppLine.getIsLatestEdition().equals("Y")){
            hmsAppeditionLineMapper.updateEdition(appEditionAppLine.getAppEditionId(),appEditionAppLine.getIsLatestEdition(),appEditionAppLine.getIsMinimumEdition());
        }
        return this.insert(iRequest,appEditionAppLine);
    }
}
