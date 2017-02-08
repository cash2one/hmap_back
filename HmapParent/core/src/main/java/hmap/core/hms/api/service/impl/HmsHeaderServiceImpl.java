package hmap.core.hms.api.service.impl;


import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.cache.impl.ApiCache;
import hmap.core.hms.api.domain.HmsInterfaceLine;
import hmap.core.hms.api.dto.HeaderAndHeaderTlDTO;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.domain.HmsInterfaceHeader;
import hmap.core.hms.api.dto.LineAndLineTlDTO;
import hmap.core.hms.api.mapper.HmsHeaderMapper;
import hmap.core.hms.api.service.IHmsHeaderService;
import hmap.core.hms.api.service.IHmsLineService;
import hmap.core.util.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiguang.sun@hand-china.com on 2016/7/21.
 */
@Service
@Transactional
public class HmsHeaderServiceImpl extends BaseServiceImpl<HmsInterfaceHeader>
    implements IHmsHeaderService {

  private final Logger logger = LoggerFactory.getLogger(HmsHeaderServiceImpl.class);
  @Autowired
  private HmsHeaderMapper hmsHeaderMapper;
  @Autowired
  private IHmsLineService hmsLineService;
  @Autowired
  private ApiCache apiCache;


  @Override
  public List<HeaderAndHeaderTlDTO> getAllHeader(HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {
    logger.info("headerAndHeaderTlDTO   pageSize:{}", headerAndHeaderTlDTO.getPagesize());

    PageHelper.startPage(headerAndHeaderTlDTO.getPage(), headerAndHeaderTlDTO.getPagesize());
    List<HeaderAndHeaderTlDTO> list = hmsHeaderMapper.getAllHeader(headerAndHeaderTlDTO);// headerAndHeaderTlDTO
    return list;


  }


  @Override
  public List<HeaderAndHeaderTlDTO> getHeaderAndLineList(
      HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {
    logger.info("headerId={}   language={}", headerAndHeaderTlDTO.getHeaderId(),
        headerAndHeaderTlDTO.getLang());
    List<HeaderAndHeaderTlDTO> list = hmsHeaderMapper.getHeaderAndLineList(headerAndHeaderTlDTO);
    if (list.isEmpty() || list.size() < 0) {
      list = hmsHeaderMapper.getHeaderByHeaderId(headerAndHeaderTlDTO);
    }
    logger.info("headers0000:" + list);
    return list;

  }

  @Override
  public HeaderAndLineDTO getHeaderAndLine(String sysName, String apiName) {
    logger.info("sysName apiName:{}", sysName + apiName);
    HeaderAndLineDTO headerAndLineDTO = apiCache.getValue(sysName + apiName);
    if (headerAndLineDTO == null) {
      HeaderAndLineDTO headerAndLineDTO1 =
          hmsHeaderMapper.getHeaderAndLineBySysNameAndApiName(sysName, apiName);
      if (headerAndLineDTO1 != null) {
        apiCache.setValue(sysName + apiName, headerAndLineDTO1);
      }
      return headerAndLineDTO1;
    } else {
      return headerAndLineDTO;

    }

  }

  /*
   * 获取所有的header和line数据——> HeaderAndHeaderTlDTO
   */
  @Override
  public List<HeaderAndLineDTO> getAllHeaderAndLine() {
    return hmsHeaderMapper.getAllHeaderAndLine();
  }

  /*
   * 获取所有的header和line数据——> HeaderAndHeaderTlDTO(分页)
   */
  @Override
  public List<HeaderAndLineDTO> getAllHeaderAndLine(PageRequest pageRequest) {
    PageHelper.startPage(pageRequest.getPage(), pageRequest.getPagesize());
    List<HeaderAndLineDTO> list = hmsHeaderMapper.getAllHeaderAndLine();
    logger.info("headerAndLine list:{}", list.get(0));
    return list;
  }

  @Override
  public List<HeaderAndHeaderTlDTO> getHeaderByHeaderId(HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {
    return hmsHeaderMapper.getHeaderByHeaderId(headerAndHeaderTlDTO);
  }

  @Override
  public HeaderAndLineDTO getHeaderAndLineByLineId(HeaderAndLineDTO headerAndLineDTO) {
    return hmsHeaderMapper.getHeaderAndLineBylineId(headerAndLineDTO);
  }

  @Override
  public int updateHeader(IRequest request, HmsInterfaceHeader hmsInterfaceHeader) {

    int result = hmsHeaderMapper.updateByPrimaryKey(hmsInterfaceHeader);
    if (result > 0) {
      // 修改头，修改后重新加入缓存
      apiCache.clear();
      apiCache.initLoad();
    }
    return result;
  }

  @Override
  public List<HeaderAndHeaderTlDTO> getAllPasswordOauthHeader() {
    List<HeaderAndHeaderTlDTO> headList = hmsHeaderMapper.getAllPasswordOauthHeader();
    return headList;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public boolean syncMockConfig(IRequest request, HeaderAndHeaderTlDTO headerAndHeaderTlDTO) {
    boolean result = true;
    int row = 0;
    LineAndLineTlDTO line = new LineAndLineTlDTO();
    line.setHeaderId(headerAndHeaderTlDTO.getHeaderId());
    line.setLang(headerAndHeaderTlDTO.getLang());
    line.setPage(1);
    line.setPagesize(100000);
    List<LineAndLineTlDTO> lines = hmsLineService.getLinesByHeaderId(line);
    for (LineAndLineTlDTO r : lines) {
      row = 0;
      HmsInterfaceLine l = new HmsInterfaceLine();
      l.setLineId(r.getLineId());
      l = hmsLineService.selectByPrimaryKey(request, l);
      l.setIsMockLine(headerAndHeaderTlDTO.getIsMock());
      if (StringUtils.isNotEmpty(l.getMockLineUrl())) {
        row = hmsLineService.updateLine(request, l);
        if (row < 1) {
          result = false;
          break;
        }
      }

    }
    return result;
  }


}
