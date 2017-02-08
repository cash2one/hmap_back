package hmap.core.util;

import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.dto.SimpleStaffResponseDTO;
import hmap.core.hms.dto.StandardStaffResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class StaffTransformer {
	public static StandardStaffResponseDTO changeDtoStandard(HmsStaff sa){
		StandardStaffResponseDTO ss = new StandardStaffResponseDTO();
			ss.setUserId(sa.getUserId());					
			ss.setEmpName(sa.getUserName());
			ss.setEmpCode(sa.getAccountNumber());
			ss.setPositionName(sa.getPosition());
			if(sa.getGenderId()==1){
				ss.setGender("男");
			}else{
				ss.setGender("女");
			}					
			ss.setDepartmentId(sa.getDepartmentId());				
			ss.setMobile(sa.getMobile());
			ss.setAvatar(sa.getAvatar());					
			ss.setWxNumber(sa.getWxNumber());						
			ss.setEnableFlag(sa.getEnableFlag());
			ss.setObjectVersionNumber(sa.getObjectVersionNumber());
			ss.setNativePlace(sa.getHomeTown());
			ss.setBaseName(sa.getBaseName());
			ss.setEmpStatus(sa.getEmpStatus());
			ss.setEmail(sa.getEmail());
			ss.setUnitName(sa.getUnitName());						
			ss.setRootUnitName(sa.getRootUnitName());						
			ss.setNamePinyin1(sa.getNamePinyin());
			ss.setNamePinyin2(sa.getNamePinyin());

		return ss;
	}
	public static List<StandardStaffResponseDTO> changeListStandard(List<HmsStaff> list){
		List<StandardStaffResponseDTO> li = new ArrayList<StandardStaffResponseDTO>();
		for(HmsStaff staff : list){
			li.add(StaffTransformer.changeDtoStandard(staff));
		}
		return li;
	}
	public static SimpleStaffResponseDTO changeDtoSimple(HmsStaff sa){
		SimpleStaffResponseDTO ss = new SimpleStaffResponseDTO();
		ss.setEmp_code(sa.getAccountNumber());
		ss.setEmp_name(sa.getUserName());
		ss.setEmp_status(sa.getEmpStatus());
		ss.setPosition_name(sa.getPosition());
		ss.setMobil(sa.getMobile());
		ss.setEmail(sa.getEmail());
		ss.setNative_place(sa.getHomeTown());
		ss.setAvatar(sa.getAvatar());
		ss.setGender(sa.getGenderId()==1L?"男":"女");
		return ss;
	}
	public static List<SimpleStaffResponseDTO> changeListSimple(List<HmsStaff> li){
		List<SimpleStaffResponseDTO> list = new ArrayList<SimpleStaffResponseDTO>();
		for(HmsStaff sa : li){
			list.add(StaffTransformer.changeDtoSimple(sa));
		}
		return list;
	}
}
