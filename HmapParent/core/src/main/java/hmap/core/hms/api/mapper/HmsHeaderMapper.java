package hmap.core.hms.api.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.api.dto.HeaderAndHeaderTlDTO;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.domain.HmsInterfaceHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jiguang.sun@hand-china.com
 * @version 2016/7/21.
 */
public interface HmsHeaderMapper extends Mapper<HmsInterfaceHeader> {

  // 查询所有的系统接口
  public List<HeaderAndHeaderTlDTO> getAllHeader(HeaderAndHeaderTlDTO headerAndHeaderTlDTO);// HeaderAndHeaderTlDTO
                                                                                            // headerAndHeaderTlDTO

  /*
   * 一对多关联查询 根据headerId 与语言
   */
  public List<HeaderAndHeaderTlDTO> getHeaderAndLineList(HeaderAndHeaderTlDTO headerAndHeaderTlDTO);

  /*
   * 根据sysName 和 apiName 查询header 和 line
   */
  public HeaderAndLineDTO getHeaderAndLineBySysNameAndApiName(@Param("sysName") String sysName,
      @Param("apiName") String apiName);

  /*
   * 获取所有的header和line 数据--->HeaderAndHeaderTlDTO
   */
  List<HeaderAndLineDTO> getAllHeaderAndLine();

  /*
   * 根据headerId 获取header
   */
  List<HeaderAndHeaderTlDTO> getHeaderByHeaderId(HeaderAndHeaderTlDTO headerAndHeaderTlDTO);

  /*
   * 根据lineId 获取数据 HeaderAndLineDTO
   */
  HeaderAndLineDTO getHeaderAndLineBylineId(HeaderAndLineDTO headerAndLineDTO);

  public List<HeaderAndHeaderTlDTO> getAllPasswordOauthHeader();
}
