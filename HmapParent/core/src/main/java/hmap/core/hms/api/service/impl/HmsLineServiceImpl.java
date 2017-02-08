package hmap.core.hms.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.cache.impl.ApiCache;
import hmap.core.hms.api.domain.HmsInterfaceLine;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.dto.LineAndLineTlDTO;
import hmap.core.hms.api.mapper.HmsLineMapper;
import hmap.core.hms.api.service.IHmsLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 */
@Service
@Transactional
public class HmsLineServiceImpl extends BaseServiceImpl<HmsInterfaceLine> implements IHmsLineService {

    private final Logger logger = LoggerFactory.getLogger(HmsLineServiceImpl.class);
    @Autowired
    private HmsLineMapper hmsLineMapper;

    @Autowired
    private ApiCache apiCache;

    @Override
    public List<LineAndLineTlDTO> getLineAndLineTl(LineAndLineTlDTO lineAndLineTlDTO) {
        return hmsLineMapper.getLineAndLineTl(lineAndLineTlDTO);
    }


    @Override
    public List<LineAndLineTlDTO> getLinesByHeaderId(LineAndLineTlDTO lineAndLineTlDTO) {

        PageHelper.startPage(lineAndLineTlDTO.getPage(), lineAndLineTlDTO.getPagesize());
        List<LineAndLineTlDTO> list = hmsLineMapper.getLinesByHeaderId(lineAndLineTlDTO);
        return list;
    }

    @Override
    public int insertLine(IRequest request, HmsInterfaceLine hmsInterfaceLine) {

        int result = hmsLineMapper.insert(hmsInterfaceLine);

        if (result > 0) {
            apiCache.reload(hmsInterfaceLine.getLineId());
        }

        return result;
    }

    @Override
    public int updateLine(IRequest request, HmsInterfaceLine hmsInterfaceLine) {

        //获取修改前的参数,查询出缓存里的数据并移除
        HeaderAndLineDTO headerAndLineDTO = hmsLineMapper.getHeaderLineByLineId(hmsInterfaceLine.getLineId());

        int result = hmsLineMapper.updateByPrimaryKey(hmsInterfaceLine);

        if (result > 0) {
            apiCache.remove(headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode());
            apiCache.reload(hmsInterfaceLine.getLineId());
        }

        return result;
    }


}
