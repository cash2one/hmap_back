package hmap.core.hms.distribution.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.distribution.domain.HmsAppDistribution;
import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import hmap.core.hms.distribution.dto.HmsAppDistributionWithVersionDTO;
import hmap.core.hms.uploader.exception.HmsUploadObjectInvalidException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Koma.Tshu on 2016/10/11.
 */
public interface IHmsAppDistributionService
    extends IBaseService<HmsAppDistribution>, ProxySelf<IHmsAppDistributionService> {
  public List<HmsAppDistributionDTO> selectAllApp(
      HmsAppDistributionDTO hmsDistributionAndVersionDTO, int pageNum, int pageSize);

  public HmsAppDistributionDTO selectAppByAppId(String appId);

  public HmsAppDistributionWithVersionDTO selectAppVersionByAppId(String appId);

  public List<HmsAppDistributionDTO> selectAppByRandomNameAndPlatform(String randomName,
      String platform);

  public List<HmsAppDistributionDTO> selectAppByRandomName(String randomName);

  public HmsAppDistributionDTO uploadAndAnalyseAppFile(MultipartFile file)
      throws IOException, HmsUploadObjectInvalidException;

  public void saveApp(IRequest iRequest, HmsAppDistributionDTO hmsDistributionAndVersionDTO)
      throws IOException;
}
