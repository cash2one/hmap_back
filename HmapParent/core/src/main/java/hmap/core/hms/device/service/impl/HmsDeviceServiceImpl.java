/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/4 0004 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.hms.device.service.impl;


import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.device.domain.HmsDevice;
import hmap.core.hms.api.exception.HmsApiException;
import hmap.core.hms.device.mapper.HmsDeviceMapper;
import hmap.core.hms.device.service.IHmsDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class HmsDeviceServiceImpl extends BaseServiceImpl<HmsDevice>
        implements IHmsDeviceService {

    @Autowired
    private HmsDeviceMapper hmsDeviceMapper;



    @Override
    public List<HmsDevice> getDiviceByUser(IRequest request, String deviceUser, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        String user = deviceUser==null||deviceUser.isEmpty()?null:deviceUser.toUpperCase();
        List<HmsDevice> e = hmsDeviceMapper.selectByUser(user);
        return  e;
    }

    @Override
    public HmsDevice selectByDeviceId(String deviceId) {
        return hmsDeviceMapper.selectByDeviceId(deviceId);
    }

    @Override
    public HmsDevice selectByIMEAndUser(String ime,String object) {
        return hmsDeviceMapper.selectByIMEAndUser(ime,object);
    }

    @Override
    public Long selectCountByUser(String deviceUser) {
        String user = deviceUser==null||deviceUser.isEmpty()?null:deviceUser.toUpperCase();
        return hmsDeviceMapper.selectCountByUser(user);
    }
    @Override
    public HmsDevice selectByIME(String ime) {
        return hmsDeviceMapper.selectByIME(ime);
    }

    @Override
    public int insertDevice(HmsDevice sysDeviceManagement) {
        sysDeviceManagement.setDeviceId(UUID.randomUUID().toString().replaceAll("\\-",""));
        return hmsDeviceMapper.insertDevice(sysDeviceManagement);
    }

    @Override
    public int updateDevice(HmsDevice hmsDevice) {
        hmsDevice.setOperationSystem(hmsDevice.getOperationSystem().toLowerCase());
        return hmsDeviceMapper.updateDevice(hmsDevice);
    }

    @Override
    public int insertDeviceWithOption(HmsDevice hmsDevice, String object) {
        hmsDevice.setDeviceId(UUID.randomUUID().toString().replaceAll("\\-",""));
        hmsDevice.setOperationSystem(hmsDevice.getOperationSystem().toLowerCase());
        return hmsDeviceMapper.insertDeviceWithOption(hmsDevice,object);
    }

    @Override
    public int insertOrUpdate(HmsDevice hmsDevice,String object) throws HmsApiException {
        int result = 0;
        HmsDevice isExist = this.selectByIMEAndUser(hmsDevice.getIme(),object);
        if(isExist==null){
            result = this.insertDeviceWithOption(hmsDevice,object);
        }else{
            if(isExist.getDeviceFlag().toUpperCase().equals("N")){
                throw new HmsApiException("the device is forbidden","设备已禁用");
            }
            result = this.updateDevice(hmsDevice);
        }
        return result;
    }

    @Override
    public int updateFlag(String deviceId) {
        return hmsDeviceMapper.updateFlag(deviceId);
    };

    @Override
    public HashMap getPlatform() {
        int android = hmsDeviceMapper.getPlatform("android");
        int ios = hmsDeviceMapper.getPlatform("ios");
        HashMap map = new HashMap();
        map.put("android",android);
        map.put("ios",ios);
        return map;
    }
    @Override
    public HashMap getStatus() {
        int y = hmsDeviceMapper.getStatus("Y");
        int n = hmsDeviceMapper.getStatus("N");
        HashMap map = new HashMap();
        map.put("Y",y);
        map.put("N",n);
        return map;
    }

    @Override
    public List<HashMap> getBars() {
        List<HashMap> results = hmsDeviceMapper.getBars();
        for(HashMap hashMap:results){
            List<HashMap> barList = (List<HashMap>) hashMap.get("barsList");
            if(barList.size()==1){
                HashMap ai = new HashMap();
                for(HashMap map:barList){
                    ai.put("operationSystemVersion",map.get("operationSystemVersion"));
                    ai.put("value",0);
                    if(map.get("operationSystem").equals("android")){
                        ai.put("operationSystem","ios");
                    }else{
                        ai.put("operationSystem","android");
                    }
                }
                barList.add(ai);
            }

        }
        return results;
    }

    @Override
    public List<HashMap> getStatistic() {
        List<HashMap> maps = new ArrayList<HashMap>();
        HashMap map = new HashMap();
        map.put("platform",this.getPlatform());
        map.put("status",this.getStatus());
        map.put("bars",this.getBars());
        maps.add(map);
        return maps;
    }
}
