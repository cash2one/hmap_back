package hmap.core.hms.api.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.api.dto.HeaderAndHeaderTlDTO;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.domain.HmsInterfaceHeader;
import hmap.core.util.PageRequest;

import java.util.List;

/**
 * @author jiguang.sun@hand-china.com
 * @version 2016/7/21.
 */
public interface IHmsHeaderService extends IBaseService<HmsInterfaceHeader>, ProxySelf<IHmsHeaderService> {


    List<HeaderAndHeaderTlDTO> getAllHeader(HeaderAndHeaderTlDTO headerAndHeaderTlDTO);

    List<HeaderAndHeaderTlDTO> getHeaderAndLineList(HeaderAndHeaderTlDTO headerAndHeaderTlDTO);

    HeaderAndLineDTO getHeaderAndLine(String sysName, String apiName);

    List<HeaderAndLineDTO> getAllHeaderAndLine();

    List<HeaderAndLineDTO> getAllHeaderAndLine(PageRequest pageRequest);

    List<HeaderAndHeaderTlDTO> getHeaderByHeaderId(HeaderAndHeaderTlDTO headerAndHeaderTlDTO);

    HeaderAndLineDTO getHeaderAndLineByLineId(HeaderAndLineDTO headerAndLineDTO);

    int updateHeader(IRequest request, HmsInterfaceHeader hmsInterfaceHeader);

    List<HeaderAndHeaderTlDTO> getAllPasswordOauthHeader();

    public boolean syncMockConfig(IRequest request,HeaderAndHeaderTlDTO headerAndHeaderTlDTO);
}
