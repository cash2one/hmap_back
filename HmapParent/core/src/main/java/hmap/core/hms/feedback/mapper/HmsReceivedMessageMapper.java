package hmap.core.hms.feedback.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;

import java.util.Date;
import java.util.List;

public interface HmsReceivedMessageMapper extends Mapper<HmsReceivedMessageDTO>{
	List<HmsReceivedMessageDTO> selectNoread(String empNo, String type);
	List<HmsReceivedMessageDTO> selectNoreadAllType(String empNo);
	List<HmsReceivedMessageDTO> selectAllNoError(String empNo);
	List<HmsReceivedMessageDTO> selectComplete(String empNo, String type);
	List<HmsReceivedMessageDTO> selectAllByType(String empNo, String type);
	HmsReceivedMessageDTO selectByAttribute1(String arg);
	List<HmsReceivedMessageDTO> selectRecent(String empNo, String type);
	List<HmsReceivedMessageDTO> selectFlightSingle(Long instanceId);
	void readAllByType(String receiver, String type);
	void deleteMessageData(Date date);
}
