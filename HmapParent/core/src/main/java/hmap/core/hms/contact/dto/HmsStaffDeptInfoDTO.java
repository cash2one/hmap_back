package hmap.core.hms.contact.dto;

import java.util.List;

public class HmsStaffDeptInfoDTO {
	private List<HmsDeptSimpleStaffDTO> deptStaff;
	private List<HmsDeptSimpleInfoDTO> deptInfo;
	private List<HmsDeptTreeDTO> childrenDeptInfo;
	private int staffNum;
	private int deptStepNum;
	private boolean hasAdmin;
	
	
	public boolean isHasAdmin() {
		return hasAdmin;
	}
	public void setHasAdmin(boolean hasAdmin) {
		this.hasAdmin = hasAdmin;
	}
	public int getStaffNum() {
		return staffNum;
	}
	public void setStaffNum(int staffNum) {
		this.staffNum = staffNum;
	}
	public int getDeptStepNum() {
		return deptStepNum;
	}
	public void setDeptStepNum(int deptStepNum) {
		this.deptStepNum = deptStepNum;
	}
	public List<HmsDeptSimpleStaffDTO> getDeptStaff() {
		return deptStaff;
	}
	public void setDeptStaff(List<HmsDeptSimpleStaffDTO> deptStaff) {
		this.deptStaff = deptStaff;
	}
	public List<HmsDeptSimpleInfoDTO> getDeptInfo() {
		return deptInfo;
	}
	public void setDeptInfo(List<HmsDeptSimpleInfoDTO> deptInfo) {
		this.deptInfo = deptInfo;
	}
	public List<HmsDeptTreeDTO> getChildrenDeptInfo() {
		return childrenDeptInfo;
	}
	public void setChildrenDeptInfo(List<HmsDeptTreeDTO> childrenDeptInfo) {
		this.childrenDeptInfo = childrenDeptInfo;
	}
	

	
}
