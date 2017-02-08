package hmap.core.hms.api.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.api.domain.HmsInterfaceLine;
import hmap.core.hms.api.dto.LineAndLineTlDTO;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 */
public interface IHmsLineService extends IBaseService<HmsInterfaceLine>,ProxySelf<IHmsLineService> {

    List<LineAndLineTlDTO> getLineAndLineTl(LineAndLineTlDTO lineAndLineTlDTO);

    List<LineAndLineTlDTO> getLinesByHeaderId(LineAndLineTlDTO lineAndLineTlDTO);

    int insertLine(IRequest request,HmsInterfaceLine hmsInterfaceLine);

    int updateLine(IRequest request,HmsInterfaceLine hmsInterfaceLine);


}
