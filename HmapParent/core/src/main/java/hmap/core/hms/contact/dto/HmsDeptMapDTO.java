package hmap.core.hms.contact.dto;

import hmap.core.hms.contact.domain.HmsStaff;

import java.util.List;

public class HmsDeptMapDTO {
	private Long departmentId;
	private String departmentName;
	private List<HmsDeptMapDTO> childrenDept;
	private List<HmsStaff> deptStaff;
	private int totalStaffNumber;
	private int staffNumber;
	
	public int getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
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
	public List<HmsDeptMapDTO> getChildrenDept() {
		return childrenDept;
	}
	public void setChildrenDept(List<HmsDeptMapDTO> childrenDept) {
		this.childrenDept = childrenDept;
	}
	public List<HmsStaff> getDeptStaff() {
		return deptStaff;
	}
	public void setDeptStaff(List<HmsStaff> deptStaff) {
		this.deptStaff = deptStaff;
	}
	public int getTotalStaffNumber() {
		return totalStaffNumber;
	}
	public void setTotalStaffNumber(int totalStaffNumber) {
		this.totalStaffNumber = totalStaffNumber;
	}

	
}
