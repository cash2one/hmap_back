/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/12 0012 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.device.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.device.domain.HmsDevice;
import hmap.core.hms.api.exception.HmsApiException;

import java.util.HashMap;
import java.util.List;

public interface IHmsDeviceService extends IBaseService<HmsDevice>,
        ProxySelf<IHmsDeviceService> {


    public List<HmsDevice> getDiviceByUser(IRequest request, String userName, int page, int pageSize);

    public HmsDevice selectByDeviceId(String deviceId);

    public HmsDevice selectByIME(String ime);

    public HmsDevice selectByIMEAndUser(String ime,String object);

    public int insertDevice(HmsDevice hmsDevice);

    public int updateFlag(String deviceId);

    public HashMap getPlatform();

    public HashMap getStatus();

    public List<HashMap> getStatistic();

    public List<HashMap> getBars();

    public Long selectCountByUser(String deviceUser);

    public int updateDevice(HmsDevice hmsDevice);

    public int insertOrUpdate(HmsDevice hmsDevice,String object) throws HmsApiException;

    public int insertDeviceWithOption(HmsDevice hmsDevice,String object);

}
