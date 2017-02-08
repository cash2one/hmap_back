package hmap.core.hms.contact.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.dto.StandardStaffResponseDTO;

public interface IHmsStaffService extends IBaseService<HmsStaff>, ProxySelf<IHmsStaffService> {
	public void init();
	public StandardStaffResponseDTO selectStaffDtoByAccountNumber(IRequest iRequest,String arg);
	public HmsStaff selectByAccountNumber(String arg);
	
	public void generateData(IRequest iRequest,HmsStaff[] staff);
	
	public void updateData(IRequest iRequest,HmsStaff[] staff);
	public void deleteData(IRequest iRequest,HmsStaff[] staff);

}
