package hmap.core.hms.appcenter.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.appcenter.domain.HmsAppAuth;

/**
 * Created by xincai.zhang on 2016/8/14.
 */
public interface HmsAppAuthMapper  extends Mapper<HmsAppAuth> {
    public HmsAppAuth selectById(String id);
}
