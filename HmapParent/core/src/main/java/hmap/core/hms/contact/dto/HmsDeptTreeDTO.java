package hmap.core.hms.contact.dto;

import java.util.List;

public class HmsDeptTreeDTO {
	private Long departmentId;
	private String departmentName;
	private List<HmsDeptTreeDTO> childrenDept;
	private List<HmsDeptSimpleStaffDTO> deptStaff;
	private int totalStaffNumber;
	private int staffNumber;
	private List<HmsDeptSimpleInfoDTO> deptInfo;
	private boolean hasAdmin;
	
	
	public boolean isHasAdmin() {
		return hasAdmin;
	}
	public void setHasAdmin(boolean hasAdmin) {
		this.hasAdmin = hasAdmin;
	}
	public List<HmsDeptSimpleInfoDTO> getDeptInfo() {
		return deptInfo;
	}
	public void setDeptInfo(List<HmsDeptSimpleInfoDTO> deptInfo) {
		this.deptInfo = deptInfo;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<HmsDeptTreeDTO> getChildrenDept() {
		return childrenDept;
	}
	public void setChildrenDept(List<HmsDeptTreeDTO> childrenDept) {
		this.childrenDept = childrenDept;
	}
	public List<HmsDeptSimpleStaffDTO> getDeptStaff() {
		return deptStaff;
	}
	public void setDeptStaff(List<HmsDeptSimpleStaffDTO> deptStaff) {
		this.deptStaff = deptStaff;
	}
	public int getTotalStaffNumber() {
		return totalStaffNumber;
	}
	public void setTotalStaffNumber(int totalStaffNumber) {
		this.totalStaffNumber = totalStaffNumber;
	}
	public int getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}
	

}
