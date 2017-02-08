package hmap.core.hms.distribution.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hmap.core.hms.distribution.domain.HmsAppDistribution;
import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Koma.Tshu on 2016/10/11.
 */
public interface HmsAppDistributionMapper extends Mapper<HmsAppDistribution> {
  public List<HmsAppDistributionDTO> selectAllApp(
      HmsAppDistributionDTO hmsDistributionAndVersionDTO);

  public HmsAppDistributionDTO selectAppByAppId(String appId);

  public List<HmsAppDistributionDTO> selectAppByRandomNameAndPlatform(
      @Param("randomCode") String randomCode, @Param("platForm") String platForm);

  public List<HmsAppDistributionDTO> selectAppByRandomName(@Param("randomCode") String randomCode);

  public List<HmsAppDistributionDTO> selectAppByBundleNameAndPlatform(
      @Param("bundleName") String bundleName, @Param("platForm") String platForm);
}
