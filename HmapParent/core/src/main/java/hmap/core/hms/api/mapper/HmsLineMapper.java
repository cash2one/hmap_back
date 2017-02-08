package hmap.core.hms.api.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.domain.HmsInterfaceLine;
import hmap.core.hms.api.dto.LineAndLineTlDTO;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 */
public interface HmsLineMapper extends Mapper<HmsInterfaceLine> {

    /*
    * 根据lineId和语言 获取LineAndLineTl
    * */
    public List<LineAndLineTlDTO> getLineAndLineTl(LineAndLineTlDTO lineAndLineTlDTO);


    /*
    * 根据lineId获取headerAndLine
    * */
    HeaderAndLineDTO getHeaderLineByLineId(String lineId);

    /*
    * 根据headerId 获取lines
    * */
    List<LineAndLineTlDTO> getLinesByHeaderId(LineAndLineTlDTO lineAndLineTlDTO);

}
