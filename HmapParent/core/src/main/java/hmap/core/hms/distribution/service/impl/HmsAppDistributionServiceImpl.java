package hmap.core.hms.distribution.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import hmap.core.hms.distribution.dto.HmsAppDistributionWithVersionDTO;
import hmap.core.hms.distribution.service.IPlistGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.common.service.IQrCodeService;
import hmap.core.hms.distribution.domain.HmsAppDistribution;
import hmap.core.hms.distribution.domain.HmsAppVersion;
import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import hmap.core.hms.distribution.mapper.HmsAppDistributionMapper;
import hmap.core.hms.distribution.service.IAnalyseAppService;
import hmap.core.hms.distribution.service.IHmsAppDistributionService;
import hmap.core.hms.distribution.service.IHmsAppVersionService;
import hmap.core.hms.uploader.dto.UploadFileWithStreamDTO;
import hmap.core.hms.uploader.exception.HmsUploadObjectInvalidException;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.uploader.service.IHmsUploadObjectService;
import hmap.core.hms.system.service.ILogService;
import hmap.core.hms.service.IRandomValueStringService;

/**
 * Created by Koma.Tshu on 2016/10/11.
 */
@Service
@Transactional
public class HmsAppDistributionServiceImpl extends BaseServiceImpl<HmsAppDistribution>
    implements IHmsAppDistributionService {
  @Autowired
  private HmsAppDistributionMapper hmsAppDistributionMapper;
  @Autowired
  private IHmsUploadObjectService hmsUploadObjectService;
  @Autowired
  private IHmsSystemConfigService hmsSystemConfigService;
  @Autowired
  private IAnalyseAppService iAnalyseAppService;
  @Autowired
  private IRandomValueStringService iRandomValueStringService;
  @Autowired
  private IHmsAppVersionService iHmsAppVersionService;
  @Autowired
  private ILogService logService;
  @Autowired
  private IQrCodeService iQrCodeService;

  @Autowired
  private IPlistGeneratorService iPlistGeneratorService;

  @Override
  public List<HmsAppDistributionDTO> selectAllApp(
      HmsAppDistributionDTO hmsDistributionAndVersionDTO, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<HmsAppDistributionDTO> list =
        hmsAppDistributionMapper.selectAllApp(hmsDistributionAndVersionDTO);
    return list;
  }

  public HmsAppDistributionDTO selectAppByAppId(String appId) {
    HmsAppDistributionDTO dto = hmsAppDistributionMapper.selectAppByAppId(appId);
    return dto;
  }

  @Override
  public HmsAppDistributionWithVersionDTO selectAppVersionByAppId(String appId) {

    HmsAppDistributionDTO appDistribution = this.selectAppByAppId(appId);
    HmsAppDistributionWithVersionDTO distributionWithVersionDTO =
        new HmsAppDistributionWithVersionDTO();
    distributionWithVersionDTO.setQrCode(appDistribution.getQrCode());
    distributionWithVersionDTO.setAppId(appDistribution.getAppId());
    distributionWithVersionDTO.setAppName(appDistribution.getAppName());
    distributionWithVersionDTO.setLastestVersion(appDistribution.getLastestVersion());
    distributionWithVersionDTO.setAppIcon(appDistribution.getAppIcon());

    List<HmsAppVersion> allVersions = iHmsAppVersionService.selectAppVersionByAppId(appId, 1, 10);
    distributionWithVersionDTO.setAllVersions(allVersions);
    return distributionWithVersionDTO;
  }



  public List<HmsAppDistributionDTO> selectAppByRandomNameAndPlatform(String randomName,
      String platform) {
    List<HmsAppDistributionDTO> list =
        hmsAppDistributionMapper.selectAppByRandomNameAndPlatform(randomName, platform);
    return list;
  }

  @Override
  public List<HmsAppDistributionDTO> selectAppByRandomName(String randomName) {
    List<HmsAppDistributionDTO> list = hmsAppDistributionMapper.selectAppByRandomName(randomName);
    return list;
  }

  private boolean isAppExists(String bundleName, String platform) {
    List<HmsAppDistributionDTO> result =
        hmsAppDistributionMapper.selectAppByBundleNameAndPlatform(bundleName, platform);
    if (result.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public HmsAppDistributionDTO uploadAndAnalyseAppFile(MultipartFile file)
      throws IOException, HmsUploadObjectInvalidException {
    long start = System.currentTimeMillis();

    HmsAppDistributionDTO result = null;
    String fileName = file.getOriginalFilename();
    this.authenticAppFileType(fileName);
    int index = fileName.lastIndexOf(".");
    String extName = fileName.substring(index + 1);
    UploadFileWithStreamDTO upd = null;
    upd = hmsUploadObjectService.uploadFileWithStream(file);
    long uploadEnd = System.currentTimeMillis();
    System.out.println("文件上传耗时" + (uploadEnd - start));

    if (extName.equalsIgnoreCase("apk")) {
      result = iAnalyseAppService.analyseApk(upd.getLocalPath());
    } else if (extName.equalsIgnoreCase("ipa")) {
      result = iAnalyseAppService.analyseIpa(upd.getFileInputStream());
    }
    long analyseEnd = System.currentTimeMillis();
    System.out.println("文件解析耗时" + (analyseEnd - start));
    result.setDownloadUrl(upd.getObjectUrl());
    return result;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  /*
   * 上传应用时，需要根据bundleid进行检查，如果不存在相同的bundle则认为是个全新的应用。 新建一个分发数据 如果存在相同的bundle则认为是版本更新，需要在原有基础上更新
   * 详细的需求逻辑为： 1.首次上传应用，需要产生主分发数据与版本行数据。版本行数据需要依赖主分发数据。其中随机码作为当前版本的versionkey字段
   * 2.再次上传应用时，需要将历史最新版本的最新版本标记更新掉，并生成一个新的二维码
   */
  public void saveApp(IRequest iRequest, HmsAppDistributionDTO hmsAppDistributionDTO)
      throws IOException {

    // 根据bundleid查询
    if (this.isAppExists(hmsAppDistributionDTO.getBundleName(),
        hmsAppDistributionDTO.getAppPlatform())) {
      // app已经存在，需要进行更新
      List<HmsAppDistributionDTO> appDistribution =
          hmsAppDistributionMapper.selectAppByBundleNameAndPlatform(
              hmsAppDistributionDTO.getBundleName(), hmsAppDistributionDTO.getAppPlatform());
      if (appDistribution.size() != 1) {
        logService.serviceLogError("获取分发版本异常");
      } else {
        HmsAppDistributionDTO instance = appDistribution.get(0);
        // 生成新版本信息
        HmsAppVersion av = new HmsAppVersion();
        av.setId(UUID.randomUUID().toString());
        av.setAppId(instance.getAppId());
        av.setDownloadTimes(0);
        av.setDownloadUrl(hmsAppDistributionDTO.getDownloadUrl());
        av.setVersion(hmsAppDistributionDTO.getLastestVersion());
        av.setIsCurrentVersion("Y");
        av.setAppSize(hmsAppDistributionDTO.getAppSize());
        av.setVersionKey(instance.getRandomCode());
        // 生成明细二维码
        String detailQrCode = iQrCodeService
            .qrCodeUpload(hmsAppDistributionDTO.getDownloadUrl().concat(av.getVersionKey()));
        av.setQrCode(detailQrCode);
        iHmsAppVersionService.insertSelective(iRequest, av);

        // 将历史数据最新版本versionKey修改成id，当前版本标记设置为N
        HmsAppVersion currentVersion =
            iHmsAppVersionService.selectCurrentVersion(instance.getAppId());
        currentVersion.setVersionKey(currentVersion.getId());
        currentVersion.setIsCurrentVersion("N");
        String currentQrCode = iQrCodeService.qrCodeUpload(
            hmsAppDistributionDTO.getDownloadUrl().concat(currentVersion.getVersionKey()));
        currentVersion.setQrCode(currentQrCode);

        if (instance.getAppPlatform().equalsIgnoreCase("ios")) {
          // 生成plist文件
          String plist = iPlistGeneratorService.createPlist(instance);
          currentVersion.setPlistUrl(plist);
        }
        iHmsAppVersionService.updateByPrimaryKeySelective(iRequest, currentVersion);
      }

    } else {
      // app还不存在，需要创建新的分发版本
      HmsAppDistribution had = new HmsAppDistribution();
      had.setAppId(UUID.randomUUID().toString());
      had.setAppName(hmsAppDistributionDTO.getAppName());
      had.setAppPlatform(hmsAppDistributionDTO.getAppPlatform());
      had.setAppIcon(hmsAppDistributionDTO.getAppIcon());
      had.setAppDescription(hmsAppDistributionDTO.getAppDescription());
      had.setBundleName(hmsAppDistributionDTO.getBundleName());
      had.setPackageName(hmsAppDistributionDTO.getPackageName());

      // 保存当前登录人，作为筛选条件
      // String userName = SecurityUtils.getCurrentUserLogin();
      // had.setCreateUser(userName);
      // 生成跳转页面的随机码
      String randomName = this.createRandomName();
      logService.serviceLogInfo("return randomName:" + randomName);
      had.setRandomCode(randomName);
      // 生成主分发二维码
      String masterQrCode =
          iQrCodeService.qrCodeUpload(hmsAppDistributionDTO.getDownloadUrl().concat(randomName));
      had.setQrCode(masterQrCode);
      this.insertSelective(iRequest, had);

      // 生成版本信息
      HmsAppVersion av = new HmsAppVersion();
      av.setId(UUID.randomUUID().toString());
      av.setAppId(had.getAppId());
      av.setDownloadTimes(0);
      av.setDownloadUrl(hmsAppDistributionDTO.getDownloadUrl());
      av.setVersion(hmsAppDistributionDTO.getLastestVersion());
      av.setIsCurrentVersion("Y");
      av.setAppSize(hmsAppDistributionDTO.getAppSize());
      av.setVersionKey(randomName);
      // 生成明细二维码
      String detailQrCode = iQrCodeService
          .qrCodeUpload(hmsAppDistributionDTO.getDownloadUrl().concat(av.getVersionKey()));
      av.setQrCode(detailQrCode);
      if (hmsAppDistributionDTO.getAppPlatform().equalsIgnoreCase("ios")) {
        String plist = iPlistGeneratorService.createPlist(hmsAppDistributionDTO);
        av.setPlistUrl(plist);
      }
      iHmsAppVersionService.insertSelective(iRequest, av);
    }


  }

  // 生成随机码
  private String createRandomName() {
    String randomName = iRandomValueStringService.getRandomString(5);
    List<HmsAppDistributionDTO> list = this.selectAppByRandomName(randomName);
    logService.serviceLogInfo("check list size:" + list.size());
    // 为了保证随机码不重复
    if (list.size() > 0) {
      createRandomName();
    }
    return randomName;
  }

  private void authenticAppFileType(String fileName) throws HmsUploadObjectInvalidException {
    boolean result = hmsUploadObjectService.authenticFileExtName(fileName, "app");
    if (!result) {
      Object[] parameters = new String[] {hmsSystemConfigService.getConfigValue("upload.appFile")};
      throw new HmsUploadObjectInvalidException(HmsUploadObjectInvalidException.EXCEPTION_CODE,
          HmsUploadObjectInvalidException.INVALIDA_FILE, parameters);
    }
  }
}
