/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.edition.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.edition.domain.HmsAppEdition;
import hmap.core.hms.edition.dto.HmapAppEditionRequestDto;
import hmap.core.hms.edition.dto.HmapAppEditionResponseDto;
import hmap.core.hms.dto.HmsAppInfoDto;

import java.util.List;

public interface IHmsAppEditionService extends
    IBaseService<HmsAppEdition>,ProxySelf<IHmsAppEditionService> {
    public HmsAppEdition selectById(String id);
    public HmsAppEdition selectByAppIdAndAppEquipment(String appId,String appEquipment);
    public List<HmsAppInfoDto> selectAppInfoList(int page,int pagesize);

    public HmsAppInfoDto selectAppInfoById(String id);

    public HmapAppEditionResponseDto selectByAppIdAndAppEquipmentAndAppEditionCode(HmapAppEditionRequestDto hmapAppEditionRequestDto);

    public HmsAppInfoDto selectAppInfoByAppIdAndAppEquipment(String appId,String appEquipment);
//    public List<HmsAppedition> selectAll();
}
