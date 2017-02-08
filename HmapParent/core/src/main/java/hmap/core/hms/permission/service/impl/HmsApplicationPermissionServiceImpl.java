/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/4 0004 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.hms.permission.service.impl;


import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.permission.domain.HmsApplicationPermission;
import hmap.core.hms.contact.domain.HmsContactTagMember;
import hmap.core.hms.contact.domain.HmsDept;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.permission.mapper.HmsApplicationPermissionMapper;
import hmap.core.hms.contact.mapper.HmsContactTagMemberMapper;
import hmap.core.hms.contact.mapper.HmsDeptMapper;
import hmap.core.hms.contact.mapper.HmsStaffMapper;
import hmap.core.hms.permission.service.IHmsApplicationPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HmsApplicationPermissionServiceImpl extends BaseServiceImpl<HmsApplicationPermission>
        implements IHmsApplicationPermissionService {
    private Logger logger = LoggerFactory
            .getLogger(HmsApplicationPermissionServiceImpl.class);

    @Autowired
    HmsApplicationPermissionMapper applicationPermissionMapper;
    @Resource
    private HmsStaffMapper sm;
    @Resource
    private HmsDeptMapper dm;
    @Resource
    private HmsContactTagMemberMapper contactTagMemberMapper;

    public final String memberType = "MEMBER";
    public final String deptType = "DEPT";
    public final String tagType = "TAG";

    @Override
    public List<HmsApplicationPermission> foungData(String dataId){
        return applicationPermissionMapper.selectByDataId(dataId);
    }
    @Override
    public boolean saveAll(IRequest iRequest,List<HmsApplicationPermission> ApplicationPermissions,String dataId){
        try{
            applicationPermissionMapper.deleteByDataId(dataId);
            for(int i=0;i<ApplicationPermissions.size();i++){
                HmsApplicationPermission app=ApplicationPermissions.get(i);
                if(applicationPermissionMapper.selectById(app.getId())!=null){
                    this.updateByPrimaryKey(iRequest,app);
                }else{
                    this.insert(iRequest, app);
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
    @Override
    public boolean checkPermission(String visibilityType, String  accountNumber){

        HmsStaff staff = sm.selectByAccountNumber(accountNumber);

        //判断member
        if(checkPermission(visibilityType,memberType,staff.getAccountNumber())){
            return true;
        }
        //dept
        if(preCheck(visibilityType,deptType)) {
            if (checkDeptPermission(
                    visibilityType, dm.selectByDeptNumber(staff.getDepartmentId()))) {
                return true;
            }
        }
        //tag
        if(preCheck(visibilityType, tagType)) {
            List<Long> deptList = new ArrayList<Long>();
            List<HmsContactTagMember>tagList =  contactTagMemberMapper.
                                            selectWithApplicationPermission(visibilityType);
            for(HmsContactTagMember tag : tagList){
                //tag中的人员循环比对
                if(tag.getMemberType().equalsIgnoreCase("member")){
                    if(checkPermissionById(visibilityType,memberType,tag.getMemberId())){
                        return true;
                    }
                    //部门收集起来一次性循环比对
                }else if(tag.getMemberType().equalsIgnoreCase("dept")){
                    HmsDept dept = dm.selectByPrimaryKey(tag.getMemberId());
                    deptList.add(dept.getDeptNumber());

                }
            }

            if(checkDeptPermission(
                    visibilityType, dm.selectByDeptNumber(
                            staff.getDepartmentId()),deptList)){
                return true;
            }

        }

        return false;
    }


    //迭代查找部门
    private boolean checkDeptPermission(String visibilityType,HmsDept dept){
        if(checkPermission(visibilityType, deptType, dept.getDeptNumber().toString())){
            return true;
        }else if(dept.getParentDeptNumber()==0L){
            return false;
        }else{
            return checkDeptPermission(
                    visibilityType,dm.selectByDeptNumber(dept.getParentDeptNumber()));
        }

    }

    //从部门列表中查找请求者的上级部门
    private boolean checkDeptPermission(String visibilityType,HmsDept dept,List<Long> deptList){
        if(dept.getParentDeptNumber()==0L){
            return false;
        }
        if(deptList.contains(dept.getDeptNumber())){
            return  true;
        }

        else{
            return checkDeptPermission(
                    visibilityType,dm.selectByDeptNumber(dept.getParentDeptNumber()),deptList);
        }

    }
    //通用方法
    //通过key查找
    private boolean checkPermission(String visibilityType,String type, String key){
        if(applicationPermissionMapper.
                selectByDataKey(visibilityType, type, key).size()>0){
            return true;
        }
        return false;
    }
    //通过id查找
    private boolean checkPermissionById(String visibilityType,String type, String id){
        if(applicationPermissionMapper.
                selectByValueId(visibilityType, type, id).size()>0){
            return true;
        }
        return false;
    }
    //仅通过type查找，看有没有相关标签
    private boolean preCheck(String visibilityType,String type){
        if(applicationPermissionMapper.
                preSelect(visibilityType, type).size()>0){
            return true;
        }
        return false;
    }

}
