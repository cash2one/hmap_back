package hmap.core.hms.distribution.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.distribution.domain.HmsAppVersion;
import hmap.core.hms.distribution.mapper.HmsAppVersionMapper;
import hmap.core.hms.distribution.service.IHmsAppVersionService;
import hmap.core.hms.system.service.ILogService;

/**
 * Created by USER on 2016/10/19.
 */
@Service
public class HmsAppVersionServiceImpl extends BaseServiceImpl<HmsAppVersion>
    implements IHmsAppVersionService {
  @Autowired
  private HmsAppVersionMapper hmsAppVersionMapper;
  @Autowired
  ILogService logService;

  public HmsAppVersion selectCurrentVersion(String appId){
    return hmsAppVersionMapper.selectCurrentVersion(appId);
  }

  @Override
  public List<HmsAppVersion> selectAppVersionByAppId(String appId,int page,int pageSize) {
    PageHelper.startPage(page, pageSize);
    return hmsAppVersionMapper.selectAppVersionByAppId(appId);
  }

  public HmsAppVersion selectVersionByKey(String versionKey) {
    return hmsAppVersionMapper.selectVersionByKey(versionKey);
  }
}
