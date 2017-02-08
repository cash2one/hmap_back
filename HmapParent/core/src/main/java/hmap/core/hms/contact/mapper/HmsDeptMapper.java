package hmap.core.hms.contact.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.contact.domain.HmsDept;
import hmap.core.hms.contact.dto.HmsDeptSimpleInfoDTO;

import java.util.List;

public interface HmsDeptMapper extends Mapper<HmsDept> {
	void deleteAll();
	List<HmsDept> selectChildren(Long dept_number);
	HmsDept selectByDeptNumber(Long dept_number);
	void deleteByDeptNumber(Long dept_number);
	List<HmsDeptSimpleInfoDTO> selectAllDept();
}
