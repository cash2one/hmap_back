package hmap.core.hms.distribution.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.distribution.domain.HmsAppVersion;

import java.util.List;

/**
 * Created by USER on 2016/10/19.
 */
public interface HmsAppVersionMapper  extends Mapper<HmsAppVersion> {
    public HmsAppVersion selectCurrentVersion(String appId);
    public HmsAppVersion selectVersionByKey(String versionKey);
    public List<HmsAppVersion> selectAppVersionByAppId(String appId);

}
