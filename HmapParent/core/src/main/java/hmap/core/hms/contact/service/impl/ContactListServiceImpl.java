/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/11/19 0019 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.service.impl;

import hmap.core.hms.contact.domain.HmsContactTag;
import hmap.core.hms.contact.domain.HmsDept;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.permission.dto.PermissionDataDTO;
import hmap.core.hms.contact.service.IContactListService;
import hmap.core.search.DeptSearchRepository;
import hmap.core.search.StaffSearchRepository;
import hmap.core.search.TagSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactListServiceImpl implements IContactListService {
  @Autowired
  TagSearchRepository tagSearchRepository;
  @Autowired
  StaffSearchRepository staffSearchRepository;
  @Autowired
  DeptSearchRepository deptSearchRepository;

  private List<PermissionDataDTO> transferHmsContactTag(List<HmsContactTag> filteredTag) {
    List<PermissionDataDTO> transferedList = new ArrayList<PermissionDataDTO>();
    for (HmsContactTag tag : filteredTag) {
      PermissionDataDTO data = new PermissionDataDTO();
      data.setDataId(tag.getId());
      data.setDataType("tag");
      data.setDataValue(tag.getTagName());
      data.setDataIcon("fa fa-tag");
      transferedList.add(data);
    }
    return transferedList;
  }

  private List<PermissionDataDTO> transferHmsStaff(List<HmsStaff> filteredStaff) {
    List<PermissionDataDTO> transferedList = new ArrayList<PermissionDataDTO>();
    for (HmsStaff staff : filteredStaff) {
      PermissionDataDTO data = new PermissionDataDTO();
      data.setDataId(staff.getUserId());
      data.setDataType("staff");
      data.setDataValue(staff.getUserName());
      data.setDataIcon("fa fa-user");
      transferedList.add(data);
    }
    return transferedList;
  }
  private List<PermissionDataDTO> transferHmsDept(List<HmsDept> filteredDept) {
    List<PermissionDataDTO> transferedList = new ArrayList<PermissionDataDTO>();
    for (HmsDept dept : filteredDept) {
      PermissionDataDTO data = new PermissionDataDTO();
      data.setDataId(String.valueOf(dept.getDeptNumber()));
      data.setDataType("dept");
      data.setDataValue(dept.getDeptName());
      data.setDataIcon("fa fa-folder");
      transferedList.add(data);
    }
    return transferedList;
  }
  @Override
  public List<PermissionDataDTO> search(String key) {
    List<HmsContactTag> filteredTag = tagSearchRepository.search(key);
    List<HmsStaff> filteredStaff = staffSearchRepository.simpleSearch(key);
    List<HmsDept> filteredDept = deptSearchRepository.search(key);
    // 将数据转成成DTO
    System.out.println("tttttttttttttttt= :"+filteredTag.get(0).getTagName());
    List<PermissionDataDTO> allResult = new ArrayList<PermissionDataDTO>();
    List<PermissionDataDTO> result = new ArrayList<PermissionDataDTO>();
    List<PermissionDataDTO> tagDTOList = this.transferHmsContactTag(filteredTag);
    List<PermissionDataDTO> staffDTOList = this.transferHmsStaff(filteredStaff);
    List<PermissionDataDTO> deptDTOList = this.transferHmsDept(filteredDept);
    allResult.addAll(deptDTOList);
    allResult.addAll(tagDTOList);
    allResult.addAll(staffDTOList);
    int resultSize=(allResult.size()>10)?10:allResult.size();

    for(int i=0;i<resultSize;i++){
      result.add(allResult.get(i));
    }
    return result;
  }
}
