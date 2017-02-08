package hmap.core.hms.device.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.device.domain.HmsDevice;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ting.liang on 2016/10/21.
 */
public interface HmsDeviceMapper extends Mapper<HmsDevice>{

    List<HmsDevice> selectByUser(@Param(value = "deviceUser") String deviceUser);

    HmsDevice selectByDeviceId(String deviceId);

    public int insertDevice(@Param(value = "hmsDevice") HmsDevice hmsDevice);

    public int updateFlag(@Param(value = "deviceId") String deviceId);

    public Integer getPlatform(@Param(value = "platform")String platform);

    public Integer getStatus(@Param(value = "flag")String flag);

    public List<HashMap> getBars();

    HmsDevice selectByIME(String ime);

    public HmsDevice selectByIMEAndUser(@Param(value = "ime") String ime,@Param(value = "object") String object);

    public Long selectCountByUser(@Param(value = "deviceUser") String deviceUser);

    public int updateDevice(@Param(value = "hmsDevice") HmsDevice hmsDevice);

    public int insertDeviceWithOption(@Param(value = "hmsDevice")HmsDevice hmsDevice,@Param(value = "object")String object);
}
