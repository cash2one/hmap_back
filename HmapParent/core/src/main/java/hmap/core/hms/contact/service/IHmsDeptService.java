package hmap.core.hms.contact.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.contact.domain.HmsDept;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.contact.dto.HmsDeptMapDTO;
import hmap.core.hms.contact.dto.HmsDeptSimpleInfoDTO;
import hmap.core.hms.contact.dto.HmsDeptTreeDTO;
import hmap.core.hms.contact.dto.HmsStaffDeptInfoDTO;

import java.util.List;

public interface IHmsDeptService extends IBaseService<HmsDept>, ProxySelf<IHmsDeptService> {
	public HmsDeptMapDTO generateTree(Long id);
	public HmsDeptMapDTO completeTree(HmsDeptMapDTO inDept);
	public HmsDeptTreeDTO getDetail(Long id,Long rootId);
	public HmsStaffDeptInfoDTO getStaffDeptInfo(String id,Long rootId);
	public void generateData(IRequest iRequest,HmsDept[] dept);
	public void updateData(IRequest iRequest,HmsDept[] dept);
	public void deleteData(IRequest iRequest,HmsDept[] dept);
	public List<HmsStaff> getStaffByDept(Long deptId,int page,int pageSize);
	public 	List<HmsDeptSimpleInfoDTO> selectAllDept();
}
