package hmap.core.hms.contact.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.cache.impl.HashStringRedisCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.contact.domain.HmsDept;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.contact.dto.*;
import hmap.core.hms.contact.mapper.HmsDeptMapper;
import hmap.core.hms.contact.mapper.HmsStaffMapper;
import hmap.core.hms.contact.service.IHmsDeptService;
import hmap.core.hms.contact.service.IHmsStaffService;
import hmap.core.search.DeptSearchRepository;
import hmap.core.util.PinyinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HmsDeptServiceImpl extends BaseServiceImpl<HmsDept> implements IHmsDeptService {
  @Resource
  private HmsDeptMapper dm;
  @Resource
  private HmsStaffMapper sm;
  @Autowired
  private IHmsStaffService staffService;
  @Autowired
  private IMessagePublisher messagePublisher;
  @Autowired
  private DeptSearchRepository deptSearchRepository;
  @Autowired
  @Qualifier("deptCache")
  private HashStringRedisCache<HmsDeptSimpleInfoDTO> deptCache;
  private Logger logger = LoggerFactory.getLogger(HmsDeptServiceImpl.class);

  // 增
  @Override
  public void generateData(IRequest iRequest, HmsDept[] dept) {
    for (HmsDept op : dept) {
      op.setDeptId(null);
      op.setDeptPinyin(PinyinUtils.getPinyin(op.getDeptName()));
      op.setDeptPinyinCapital(PinyinUtils.getPinyinCapital(op.getDeptName()));
      self().insertSelective(iRequest, op);
      messagePublisher.publish("public.es.dept.syn.insert", op);
    }
  }

  // 改
  @Override
  public void updateData(IRequest iRequest, HmsDept[] dept) {
    for (HmsDept op : dept) {
      op.setDeptId(dm.selectByDeptNumber(op.getDeptNumber()).getDeptId());
      op.setDeptPinyin(PinyinUtils.getPinyin(op.getDeptName()));
      op.setDeptPinyinCapital(PinyinUtils.getPinyinCapital(op.getDeptName()));
      this.updateByPrimaryKey(iRequest, op);
      messagePublisher.publish("public.es.dept.syn.insert", op);
    }
  }

  // 删
  @Override
  public void deleteData(IRequest iRequest, HmsDept[] dept) {
    for (HmsDept op : dept) {
      dm.deleteByDeptNumber(op.getDeptNumber());

    }

  }

  // 生成一颗部门树,并收集每个部门的人数
  public HmsDeptMapDTO generateTree(Long id) {
    HmsDeptMapDTO hrmsDeptMapDto = new HmsDeptMapDTO();

    hrmsDeptMapDto.setDepartmentId(id);

    HmsDept dapt = dm.selectByDeptNumber(id);
    hrmsDeptMapDto.setDepartmentName(dapt.getDeptName());

    List<HmsStaff> staffList = sm.selectByDeptIdOrderByName(id);
    hrmsDeptMapDto.setDeptStaff(staffList);
    if (staffList != null && staffList.size() != 0) {
      hrmsDeptMapDto.setStaffNumber(staffList.size());
    } else {
      hrmsDeptMapDto.setStaffNumber(0);
    }

    List<HmsDeptMapDTO> childList = new ArrayList<HmsDeptMapDTO>();
    List<HmsDept> chilrenList = dm.selectChildren(id);
    if (chilrenList != null && chilrenList.size() != 0) {
      for (HmsDept dept : chilrenList) {
        HmsDeptMapDTO hrmsDeptMapDto2 = generateTree(dept.getDeptNumber());
        childList.add(hrmsDeptMapDto2);
      }
    }
    hrmsDeptMapDto.setChildrenDept(childList);
    return hrmsDeptMapDto;
  }

  // 收集部门树里每个部门（包括它的自部门）的总人数
  public HmsDeptMapDTO completeTree(HmsDeptMapDTO inDept) {
    int total = 0;
    total += inDept.getStaffNumber();
    List<HmsDeptMapDTO> childList = inDept.getChildrenDept();
    if (childList != null && childList.size() != 0) {
      for (HmsDeptMapDTO h : childList) {
        HmsDeptMapDTO hrmsDeptMapDto = completeTree(h);
        total += hrmsDeptMapDto.getTotalStaffNumber();
      }
    }
    inDept.setTotalStaffNumber(total);
    HmsDept dept = dm.selectByDeptNumber(inDept.getDepartmentId());
    if (total != 0) {
      dept.setTotalStaffNumber((long) total);
      dm.updateByPrimaryKeySelective(dept);
    } else {
      dept.setTotalStaffNumber(0L);
      dm.updateByPrimaryKeySelective(dept);
    }
    return inDept;
  }

  // 查
  @Override
  public HmsDeptTreeDTO getDetail(Long id, Long rootId) {
    HmsDeptTreeDTO hrmsDeptTreeDto = new HmsDeptTreeDTO();
    List<HmsDeptTreeDTO> childDeptList = new ArrayList<HmsDeptTreeDTO>();
    List<HmsDeptSimpleStaffDTO> deptStaff = new ArrayList<HmsDeptSimpleStaffDTO>();
    HmsDept dept = dm.selectByDeptNumber(id);
    List<HmsStaff> staffList = new ArrayList<HmsStaff>();

    hrmsDeptTreeDto.setDepartmentId(dept.getDeptNumber());
    hrmsDeptTreeDto.setDepartmentName(dept.getDeptName());
    if (staffList != null) {
      hrmsDeptTreeDto.setStaffNumber(staffList.size());
    }
    hrmsDeptTreeDto.setTotalStaffNumber(dept.getTotalStaffNumber().intValue());
    List<HmsDept> chilrenDeptList = dm.selectChildren(id);
    hrmsDeptTreeDto.setHasAdmin(false);
    if (chilrenDeptList.isEmpty()) {
      staffList = sm.selectByDeptIdOrderByNameAndPosition(id);
      if (staffList.size() >= 2 && staffList.get(0).getPosition().indexOf("经理") != -1
          && staffList.get(1).getPosition().indexOf("经理") == -1) {
        hrmsDeptTreeDto.setHasAdmin(true);
      }
    } else {
      staffList = sm.selectByDeptIdOrderByName(id);
    }

    for (HmsStaff staff : staffList) {
      HmsDeptSimpleStaffDTO h = new HmsDeptSimpleStaffDTO();
      h.setUserId(staff.getUserId());
      h.setUserName(staff.getUserName());
      h.setGenderId((staff.getGenderId() == 1) ? "男" : "女");
      h.setAvatar(staff.getAvatar());
      h.setAccountNumber(staff.getAccountNumber());
      deptStaff.add(h);
    }
    hrmsDeptTreeDto.setDeptStaff(deptStaff);
    for (HmsDept d : chilrenDeptList) {
      HmsDeptTreeDTO h = new HmsDeptTreeDTO();
      h.setDepartmentId(d.getDeptNumber());
      h.setDepartmentName(d.getDeptName());
      h.setTotalStaffNumber(d.getTotalStaffNumber().intValue());
      childDeptList.add(h);
    }
    hrmsDeptTreeDto.setChildrenDept(childDeptList);

    List<HmsDeptSimpleInfoDTO> returnDeptList = new ArrayList<HmsDeptSimpleInfoDTO>();
    HmsDeptSimpleInfoDTO hrmsDeptSimpleInfo = new HmsDeptSimpleInfoDTO();
    hrmsDeptSimpleInfo.setId(dept.getDeptNumber());
    hrmsDeptSimpleInfo.setName(dept.getDeptName());
    hrmsDeptSimpleInfo.setParentId(dept.getParentDeptNumber());
    returnDeptList.add(hrmsDeptSimpleInfo);
    returnDeptList = getDeptList(returnDeptList, rootId);
    hrmsDeptTreeDto.setDeptInfo(returnDeptList);

    return hrmsDeptTreeDto;
  }

  /***
   * 查找当前员工的组织架构
   *
   *
   * @return
   */
  @Override
  public HmsStaffDeptInfoDTO getStaffDeptInfo(String id, Long rootId) {
    HmsStaffDeptInfoDTO hrmsStaffDeptInfo = new HmsStaffDeptInfoDTO();
    HmsStaff staff = staffService.selectByAccountNumber(id);
    HmsDept dept = dm.selectByDeptNumber(staff.getDepartmentId());
    List<HmsDeptSimpleStaffDTO> returnStaffList = new ArrayList<HmsDeptSimpleStaffDTO>();
    List<HmsStaff> list = new ArrayList<HmsStaff>();
    hrmsStaffDeptInfo.setHasAdmin(false);
    List<HmsDept> childrenDeptInfo = dm.selectChildren(dept.getDeptNumber());
    if (childrenDeptInfo.isEmpty()) {
      list = sm.selectByDeptIdOrderByNameAndPosition(staff.getDepartmentId());
      if (list.size() >= 2 && list.get(0).getPosition().indexOf("经理") != -1
          && list.get(1).getPosition().indexOf("经理") == -1) {
        hrmsStaffDeptInfo.setHasAdmin(true);
      }
    } else {
      list = sm.selectByDeptIdOrderByName(staff.getDepartmentId());
    }
    for (HmsStaff s : list) {
      HmsDeptSimpleStaffDTO h = new HmsDeptSimpleStaffDTO();
      h.setAccountNumber(s.getAccountNumber());
      h.setAvatar(s.getAvatar());
      h.setGenderId((s.getGenderId() == 1) ? "男" : "女");
      h.setUserId(s.getUserId());
      h.setUserName(s.getUserName());
      returnStaffList.add(h);
    }
    hrmsStaffDeptInfo.setDeptStaff(returnStaffList);

    List<HmsDeptTreeDTO> childDeptList = new ArrayList<HmsDeptTreeDTO>();
    for (HmsDept d : childrenDeptInfo) {
      HmsDeptTreeDTO h = new HmsDeptTreeDTO();
      h.setDepartmentId(d.getDeptNumber());
      h.setDepartmentName(d.getDeptName());
      h.setTotalStaffNumber(d.getTotalStaffNumber().intValue());
      childDeptList.add(h);
    }
    hrmsStaffDeptInfo.setChildrenDeptInfo(childDeptList);

    List<HmsDeptSimpleInfoDTO> returnDeptList = new ArrayList<HmsDeptSimpleInfoDTO>();
    HmsDeptSimpleInfoDTO hrmsDeptSimpleInfo = new HmsDeptSimpleInfoDTO();
    hrmsDeptSimpleInfo.setId(dept.getDeptNumber());
    hrmsDeptSimpleInfo.setName(dept.getDeptName());
    hrmsDeptSimpleInfo.setParentId(dept.getParentDeptNumber());
    returnDeptList.add(hrmsDeptSimpleInfo);
    returnDeptList = getDeptList(returnDeptList, rootId);
    hrmsStaffDeptInfo.setDeptInfo(returnDeptList);
    hrmsStaffDeptInfo.setStaffNum(returnStaffList.size());
    hrmsStaffDeptInfo.setDeptStepNum(returnDeptList.size());

    return hrmsStaffDeptInfo;
  }

  // 迭代查找上层组织架构,在上面的方法里使用
  private List<HmsDeptSimpleInfoDTO> getDeptList(List<HmsDeptSimpleInfoDTO> list, Long rootId) {
    HmsDeptSimpleInfoDTO h = list.get(0);
    Long id = h.getId();
    if (id != rootId) {
      HmsDept dept = dm.selectByDeptNumber(h.getParentId());
      HmsDeptSimpleInfoDTO newH = new HmsDeptSimpleInfoDTO();
      newH.setId(dept.getDeptNumber());
      newH.setName(dept.getDeptName());
      newH.setParentId(dept.getParentDeptNumber());
      // list.add(newH);
      list.add(0, newH);
      getDeptList(list, rootId);
    }
    return list;
  }

  public List<HmsStaff> getStaffByDept(Long deptId, int page, int pageSize) {
    PageHelper.startPage(page, pageSize);
    List<HmsStaff> list = sm.selectByDeptIdOrderByNameAndPosition(deptId);
    return list;
  }

  @Override public List<HmsDeptSimpleInfoDTO> selectAllDept() {
//    List<HmapDeptSimpleInfoDTO> allDept=dm.selectAllDept();
    //deptCache.getAll();
    return dm.selectAllDept();
  }

  //@PostConstruct
  public void init() {
    if (deptSearchRepository != null && deptSearchRepository.count() < 1) {
      List<HmsDept> allDept = self().selectAll();
      if (allDept != null && allDept.size() > 0) {
        deptSearchRepository.save(allDept);
      }
    }
  }
}
