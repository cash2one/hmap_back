package hmap.core.hms.contact.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.contact.domain.HmsStaff;

import java.util.List;

public interface HmsStaffMapper extends Mapper<HmsStaff> {
	HmsStaff selectByAccountNumber(String arg);
	List<HmsStaff> selectByDeptIdOrderByName(Long id);
	List<HmsStaff> selectByDeptIdOrderByNameAndPosition(Long id);
	void deleteByAccountNumber(String arg);
}
