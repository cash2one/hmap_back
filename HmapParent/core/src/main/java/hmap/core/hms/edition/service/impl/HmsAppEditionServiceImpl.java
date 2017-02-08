/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.edition.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.edition.domain.HmsAppEdition;
import hmap.core.hms.edition.dto.HmapAppEditionRequestDto;
import hmap.core.hms.edition.dto.HmapAppEditionResponseDto;
import hmap.core.hms.dto.HmsAppInfoDto;
import hmap.core.hms.edition.mapper.HmsAppEditionMapper;
import hmap.core.hms.edition.service.IHmsAppEditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class HmsAppEditionServiceImpl extends BaseServiceImpl<HmsAppEdition>
    implements IHmsAppEditionService {
    @Autowired
    private HmsAppEditionMapper appeditionAppMapper;
    public HmsAppEdition selectById(String id){
      return  appeditionAppMapper.selectById(id);
    }
    public HmsAppEdition selectByAppIdAndAppEquipment(String appId,String appEquipment) {
        return appeditionAppMapper.selectByAppIdAndAppEquipment(appId, appEquipment);
    }

    @Override
    public List<HmsAppInfoDto> selectAppInfoList(int page,int pagesize) {
        PageHelper.startPage(page,pagesize);
        return appeditionAppMapper.selectAppInfoList();
    }

    @Override
    public HmsAppInfoDto selectAppInfoById(String id) {
        return appeditionAppMapper.selectAppInfoById(id);
    }

    @Override
    public HmapAppEditionResponseDto selectByAppIdAndAppEquipmentAndAppEditionCode(HmapAppEditionRequestDto hmapAppEditionRequestDto) {
        List<HashMap> appEditionInfoList =  appeditionAppMapper.selectAppEditionInfo(hmapAppEditionRequestDto);
        int minimumEditionOrderNum = -1;
        int latestEditionOrderNum = -1;
        int nowEditionOrderNum = -1;
        for(HashMap appEditionInfo:appEditionInfoList){
            Object isMinimumEdition =  appEditionInfo.get("isMinimumEdition");
            Object isLatestEdition =  appEditionInfo.get("isLatestEdition");
            Object editionCode = appEditionInfo.get("editionCode");
            if(isMinimumEdition!=null&&isMinimumEdition.toString().equals("Y")){
               minimumEditionOrderNum = Integer.parseInt(appEditionInfo.get("orderNum").toString());
            }
            if(isLatestEdition!=null&&isLatestEdition.toString().equals("Y")){
                latestEditionOrderNum = Integer.parseInt(appEditionInfo.get("orderNum").toString());
            }
            if(editionCode!=null && editionCode.toString().equals(hmapAppEditionRequestDto.getAppEditionCode())){
                nowEditionOrderNum = Integer.parseInt(appEditionInfo.get("orderNum").toString());
            }
        }
        HmapAppEditionResponseDto hmapAppEditionResponseDto = new HmapAppEditionResponseDto();
        String isForceUpdate = "N";
        if(minimumEditionOrderNum==-1 && latestEditionOrderNum==-1){
            isForceUpdate ="N";
            if(hmapAppEditionResponseDto==null){
                hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto, "MaxOrderNum",latestEditionOrderNum,minimumEditionOrderNum,nowEditionOrderNum);
                isForceUpdate ="N";
            }
        }else if(minimumEditionOrderNum ==-1 && latestEditionOrderNum!=-1){
            hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto,"IsLatestEditionInApplicationStore",latestEditionOrderNum,minimumEditionOrderNum,nowEditionOrderNum);
            isForceUpdate ="N";
            if(hmapAppEditionResponseDto==null){
                hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto,"IsLatestEditionIsY",latestEditionOrderNum,minimumEditionOrderNum,nowEditionOrderNum);
                isForceUpdate ="N";
            }
        }else if(minimumEditionOrderNum !=-1) {
            if (nowEditionOrderNum == -1 || nowEditionOrderNum < minimumEditionOrderNum) {
                hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto, "IsMinimumEditionInApplicationStore", latestEditionOrderNum, minimumEditionOrderNum, nowEditionOrderNum);
                isForceUpdate ="Y";
                if (hmapAppEditionResponseDto == null) {
                    hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto, "IsMinimumEditionIsY", latestEditionOrderNum, minimumEditionOrderNum, nowEditionOrderNum);
                    isForceUpdate ="Y";
                }
            } else {
                if (latestEditionOrderNum == -1) {
                    hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto, "IsLatestEditionInApplicationStore", latestEditionOrderNum, minimumEditionOrderNum, nowEditionOrderNum);
                    isForceUpdate ="N";
                    if (hmapAppEditionResponseDto == null) {
                        hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto, "MaxOrderNum", latestEditionOrderNum, minimumEditionOrderNum, nowEditionOrderNum);
                        isForceUpdate ="N";
                    }
                } else {
                    hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto, "IsLatestEditionInApplicationStore", latestEditionOrderNum, minimumEditionOrderNum, nowEditionOrderNum);
                    isForceUpdate ="N";
                    if (hmapAppEditionResponseDto == null) {
                        hmapAppEditionResponseDto = appeditionAppMapper.selectByAppIdAndAppEquipmentAndAppEditionCode(hmapAppEditionRequestDto, "IsLatestEditionIsY", latestEditionOrderNum, minimumEditionOrderNum, nowEditionOrderNum);
                        isForceUpdate ="N";
                    }
                }
            }
        }
        if(hmapAppEditionResponseDto!=null){
            hmapAppEditionResponseDto.setIsForceUpdate(isForceUpdate);
        }
        return hmapAppEditionResponseDto;
    }

    @Override
    public HmsAppInfoDto selectAppInfoByAppIdAndAppEquipment(String appId, String appEquipment) {
        return appeditionAppMapper.selectAppInfoByAppIdAndAppEquipment(appId,appEquipment);
    }


    //    public List<HmsAppedition> selectAll(){
//        return  appeditionAppMapper.selectAll();
//    }
}
