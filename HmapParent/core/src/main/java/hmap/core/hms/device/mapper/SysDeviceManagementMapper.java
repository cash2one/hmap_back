package hmap.core.hms.device.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.domain.SysDeviceManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ting.liang on 2016/10/21.
 */
public interface SysDeviceManagementMapper extends Mapper<SysDeviceManagement>{

    List<SysDeviceManagement> selectByUser(@Param(value = "deviceUser") String deviceUser);

    SysDeviceManagement selectByDeviceId(String deviceId);

    public int insertDevice(@Param(value = "sysDeviceManagement") SysDeviceManagement sysDeviceManagement);

    public int updateFlag(@Param(value = "deviceId") String deviceId);
}
