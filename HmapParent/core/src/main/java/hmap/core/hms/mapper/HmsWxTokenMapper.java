package hmap.core.hms.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.domain.HmsWxToken;

/**
 * Created by machenike on 2016/9/12.
 */
public interface HmsWxTokenMapper  extends Mapper<HmsWxToken> {
    HmsWxToken selectByAppId(String arg);
}
