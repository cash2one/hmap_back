/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.mapper 
 * Date:2016/7/31 0031
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.edition.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.edition.domain.HmsAppEdition;
import hmap.core.hms.edition.dto.HmapAppEditionRequestDto;
import hmap.core.hms.edition.dto.HmapAppEditionResponseDto;
import hmap.core.hms.dto.HmsAppInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface HmsAppEditionMapper extends Mapper<HmsAppEdition> {
    public HmsAppEdition selectById(String id);
    public HmsAppEdition selectByAppIdAndAppEquipment(@Param("appId") String appId,@Param("appEquipment") String appEquipment);

    public List<HmsAppInfoDto> selectAppInfoList();

    public HmsAppInfoDto selectAppInfoById(@Param("id")String id);

    public HmapAppEditionResponseDto selectByAppIdAndAppEquipmentAndAppEditionCode(@Param("hmapAppEditionRequestDto")HmapAppEditionRequestDto hmapAppEditionRequestDto
            ,@Param("condition")String condition,@Param("latestEditionOrderNum")int latestEditionOrderNum,@Param("minimumEditionOrderNum")int minimumEditionOrderNum
            ,@Param("nowEditionOrderNum")int nowEditionOrderNum);

    public List<HashMap> selectAppEditionInfo(@Param("hmapAppEditionRequestDto")HmapAppEditionRequestDto hmapAppEditionRequestDto);
    public HmsAppInfoDto selectAppInfoByAppIdAndAppEquipment(@Param("appId") String appId,@Param("appEquipment") String appEquipment);
//    public List<HmsAppedition> selectAll();
}
