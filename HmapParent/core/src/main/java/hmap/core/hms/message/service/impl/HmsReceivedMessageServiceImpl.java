package hmap.core.hms.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.message.dto.HmsMessageTypeDTO;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.message.dto.MessageListDTO;
import hmap.core.hms.message.dto.MessageReturnDTO;
import hmap.core.hms.message.mapper.HmsMessageTypeMapper;
import hmap.core.hms.feedback.mapper.HmsReceivedMessageMapper;
import hmap.core.hms.message.service.IHmsReceivedMessageService;
import hmap.core.hms.contact.service.IHmsStaffService;
import hmap.core.util.CountDateDiff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HmsReceivedMessageServiceImpl extends
		BaseServiceImpl<HmsReceivedMessageDTO> implements
		IHmsReceivedMessageService {
	@Resource
	private HmsReceivedMessageMapper hrmsReceivedMessageMapper;
	@Resource
	private HmsMessageTypeMapper messageTypeMapper;
	@Autowired
	private IHmsStaffService staffService;
	
	private Logger logger = LoggerFactory.getLogger(HmsReceivedMessageServiceImpl.class);
	
	
	SimpleDateFormat standardSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat recentsdf = new SimpleDateFormat(" HH:mm");
	SimpleDateFormat nomalSdf = new SimpleDateFormat("MM-dd HH:mm");

	private PackageDTO getBriefByType(String empNo,HmsMessageTypeDTO type,PackageDTO packageDTO){
		Date nowDate = new Date();
		List<HmsReceivedMessageDTO> dtoList = new ArrayList<HmsReceivedMessageDTO>();
		List<HmsReceivedMessageDTO> returnList = packageDTO.getReturnList();
		MessageListDTO messageListDto = new MessageListDTO();
		HmsReceivedMessageDTO dtoRecent = null;
		List<Integer> deleteList = new ArrayList<Integer>();
		for(int i=0;i<returnList.size();i++){
			HmsReceivedMessageDTO dto = returnList.get(i);
			if(dto.getMessageType().equals(type.getMessageType())){
				dtoList.add(dto);
				deleteList.add(i);
			}
		}
		for(int i = deleteList.size()-1;i>=0;i--){
			returnList.remove(deleteList.get(i).intValue());
		}
		if(dtoList.size()==0){
			List<HmsReceivedMessageDTO> list = hrmsReceivedMessageMapper.selectRecent(empNo, type.getMessageType());
			if(list!=null&&list.size()>0&&list.get(0)!=null){
				String time = sdf.format(list.get(0).getCreationDate());
				messageListDto.setLatestMessageTime(time);
				packageDTO.setTimestamp(Long.parseLong(time));
				messageListDto.setCompareTime(transformDate(list.get(0).getCreationDate(),nowDate));
				messageListDto.setMessageContent(list.get(0).getContent());
			}
		}else{
			dtoRecent=dtoList.get(0);
			String time = sdf.format(dtoRecent.getCreationDate());
			messageListDto.setLatestMessageTime(time);
			packageDTO.setTimestamp(Long.parseLong(time));
			messageListDto.setCompareTime(transformDate(dtoRecent.getCreationDate(),nowDate));
			messageListDto.setMessageContent(dtoRecent.getContent());
		}
		messageListDto.setMessageTypeCode(type.getMessageType());

		messageListDto.setMessageTypeDesc(type.getMessageDesc());
		messageListDto.setMessageNum(dtoList.size() + "");
		packageDTO.setMessageListDTO(messageListDto);
		packageDTO.setReturnList(returnList);
		return packageDTO;
	}
	private List<MessageListDTO> getBrief(String empNo) {
		List<HmsMessageTypeDTO> typeList = messageTypeMapper.selectAll();


		long[] sort = new long[typeList.size()];

		List<MessageListDTO> messageList = new ArrayList<MessageListDTO>();
		Map<Long,MessageListDTO> map = new HashMap<Long, MessageListDTO>();

		List<HmsReceivedMessageDTO> returnList = hrmsReceivedMessageMapper.selectNoreadAllType(empNo);
		for(int i =0;i<typeList.size();i++){
			PackageDTO packageDTO = new PackageDTO();
			Long timestamp = Long.valueOf(i);
			packageDTO.setTimestamp(timestamp);
			packageDTO.setReturnList(returnList);
			packageDTO = this.getBriefByType(empNo,typeList.get(i),packageDTO);
			map.put(packageDTO.getTimestamp(),packageDTO.getMessageListDTO());
			sort[i]=packageDTO.getTimestamp();
			returnList = packageDTO.getReturnList();
		}
		Arrays.sort(sort);
		for(int i =sort.length-1;i>=0;i--){
			messageList.add(map.get(sort[i]));
		}
		return messageList;
	}

	

	@Override
	public MessageReturnDTO getBriefMessage(String empNo) {
		MessageReturnDTO messageReturnDto = new MessageReturnDTO("S",
				getBrief(empNo));
		messageReturnDto.setLatestTime(sdf.format(new Date()));
		return messageReturnDto;
	}



	@Override
	public MessageReturnDTO getDetailMessage(String empNo, String type,
			String page) {
		List<MessageListDTO> messageList = new ArrayList<MessageListDTO>();


		PageHelper.startPage(Integer.parseInt(page), 20);
		List<HmsReceivedMessageDTO> receiveList = hrmsReceivedMessageMapper
				.selectAllByType(empNo, type);
		for (HmsReceivedMessageDTO msd : receiveList) {
			MessageListDTO messageListDto = new MessageListDTO();
			List<Map<String, String>> messageDetail = new ArrayList<Map<String, String>>();
			List<Map<String, String>> messageSecret = new ArrayList<Map<String, String>>();
			messageListDto.setMessageId(msd.getId());
			messageListDto.setPushMessageId(msd.getReturnId());
			messageListDto.setMessageTypeCode(type);
			messageListDto.setStatus(msd.getStatus());
			messageListDto.setSendTime(standardSdf.format(msd.getCreationDate()));

			HmsMessageTypeDTO typeDTO = messageTypeMapper.selectByType(type);

			messageListDto.setMessageTypeDesc(typeDTO==null?null:typeDTO.getMessageDesc());
			messageListDto.setMessageContent(msd.getContent());
			if (type.equals("work_flow")) {
				Map<String, String> instanceIdMap = new HashMap<String, String>();
				instanceIdMap.put("name", "instanceId");
				instanceIdMap.put("value", msd.getInstanceId() != null ? msd
						.getInstanceId().toString() : null);
				Map<String, String> sourceInstanceIdMap = new HashMap<String, String>();
				sourceInstanceIdMap.put("name", "sourceInstanceId");
				sourceInstanceIdMap.put("value", msd.getSourceInstanceId());
				Map<String, String> nodeIdMap = new HashMap<String, String>();
				nodeIdMap.put("name", "nodeId");
				nodeIdMap.put("value", msd.getSourceNodeId());
				Map<String, String> recordIdMap = new HashMap<String, String>();
				recordIdMap.put("name", "recordId");
				recordIdMap.put("value", msd.getSourceRecordId());
				Map<String, String> workflowIdMap = new HashMap<String, String>();
				workflowIdMap.put("name", "workflowId");
				workflowIdMap.put("value", msd.getSourceWorkflowId());
				if (msd.getContentDesc() != null) {
					Map<String, String> contentDescMap = new HashMap<String, String>();
					contentDescMap.put("name", "备注");
					contentDescMap.put("value", msd.getContentDesc());
					messageDetail.add(contentDescMap);
				}
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("name", "申请名称");
				contentMap.put("value", msd.getContent());
				messageSecret.add(instanceIdMap);
				messageSecret.add(sourceInstanceIdMap);
				messageSecret.add(nodeIdMap);
				messageSecret.add(recordIdMap);
				messageSecret.add(workflowIdMap);
				messageDetail.add(contentMap);
				messageListDto.setMessageDetail(messageDetail);
				messageListDto.setMessageSecret(messageSecret);
			}
			messageList.add(messageListDto);
		}
		MessageReturnDTO messageReturnDto = new MessageReturnDTO("S",
				messageList);
		return messageReturnDto;
	}

	@Override
	public MessageReturnDTO deleteMessage(String id) {
		try {
			hrmsReceivedMessageMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageReturnDTO("N", null);
		}
		return new MessageReturnDTO("S", null);
	}

	@Override
	public MessageReturnDTO changeMessage(String id, String empNo) {
		HmsReceivedMessageDTO hrm = hrmsReceivedMessageMapper
				.selectByPrimaryKey(id);
		String returnMsg = null;
		try {
			if (hrm.getStatus().equals("NOREAD")) {
				hrm.setStatus("COMPLETE");
				returnMsg = "已设置为已读";
			} else if (hrm.getStatus().equals("COMPLETE")) {
				hrm.setStatus("NOREAD");
				returnMsg = "已设置为未读";
			}
			hrmsReceivedMessageMapper.updateByPrimaryKeySelective(hrm);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageReturnDTO("N", null);
		}
		MessageReturnDTO messageReturnDto = new MessageReturnDTO("S",
				returnMsg, getBrief(empNo));
		return messageReturnDto;
	}
	

	
	private String transformDate(Date aDate, Date bDate){
		if(CountDateDiff.daysOfTwo(aDate,bDate)==0){
			return recentsdf.format(aDate);
		}else if(CountDateDiff.daysOfTwo(aDate,bDate)==1){
			return "昨天"+recentsdf.format(aDate);
		}else{
			return nomalSdf.format(aDate);
		}
	}
	
	public boolean readAllByType(String empNo,String type){
		try{
		hrmsReceivedMessageMapper.readAllByType(empNo, type);
		return true;
		}catch(Exception e){
			logger.error("error:"+e.toString());
			return false;
		}
	}
	private class PackageDTO{
		public Long timestamp;
		public List<HmsReceivedMessageDTO> returnList;
		public MessageListDTO messageListDTO;

		public Long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}

		public List<HmsReceivedMessageDTO> getReturnList() {
			return returnList;
		}

		public void setReturnList(List<HmsReceivedMessageDTO> returnList) {
			this.returnList = returnList;
		}

		public MessageListDTO getMessageListDTO() {
			return messageListDTO;
		}

		public void setMessageListDTO(MessageListDTO messageListDTO) {
			this.messageListDTO = messageListDTO;
		}
	}

}
