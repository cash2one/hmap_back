package hmap.core.hms.system.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.system.domain.SysUser;

import java.util.List;

/**
 * Created by ting.liang on 2016/10/21.
 */
public interface SysUserMapper extends Mapper<SysUser>{
    List<SysUser> selectByUserName(String userName);
}
