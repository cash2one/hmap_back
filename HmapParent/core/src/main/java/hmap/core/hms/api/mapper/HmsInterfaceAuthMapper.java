package hmap.core.hms.api.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.api.domain.HmsInterfaceAuth;
import hmap.core.hms.api.dto.HmsInterfaceAuthDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Koma.Tshu on 2016/8/25.
 */
public interface HmsInterfaceAuthMapper extends Mapper<HmsInterfaceAuth> {
  // 根据headerId查询授权信息
  public List<HmsInterfaceAuthDTO> selectAuthByHeaderId(String headerId);
  // 根据lineId查询授权信息
  public List<HmsInterfaceAuthDTO> selectAuthByLineId(String lineId);

  public HmsInterfaceAuthDTO selectHeadAuthByAppidAndSysName(@Param("appid") String appid,
      @Param("sysName") String sysName);

  public HmsInterfaceAuthDTO selectLineAuthByAppidAndSysNameAndUrl(@Param("appid") String appid,
      @Param("sysName") String sysName, @Param("url") String url);

  public HmsInterfaceAuthDTO selectLineAuthByAppidAndSysNameAndApiName(@Param("appid") String appid,
      @Param("sysName") String sysName, @Param("apiName") String apiName);
}
