package hmap.core.util;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JPushJsonHelper {
	public static String formBody(HmsReceivedMessageDTO requestDto) {
	    Map<Object, Object> map = new HashMap<Object, Object>();
	    Map<String, String> messageMap = new HashMap<String, String>();
	    Map<String, Object> androidMap = new HashMap<String, Object>();
	    Map<String, Object> iosMap = new HashMap<String, Object>();
	    Map<Object, Object> tagMap = new HashMap<Object, Object>();

	    Map<String, Object> options = new HashMap<String, Object>();
	    
	    Map<String, String> extrasMap = new HashMap<String, String>();
	    extrasMap.put("source_instance_id",requestDto.getSourceInstanceId());
	    extrasMap.put("source_node_id",requestDto.getSourceNodeId());
	    extrasMap.put("source_record_id", requestDto.getSourceRecordId());
	    extrasMap.put("source_workflow_id", requestDto.getSourceWorkflowId());
	    extrasMap.put("message_type", requestDto.getMessageType());
	    extrasMap.put("message_id", requestDto.getId());
	    
	    map.put("platform", "all");

		//如果是all
		if(requestDto.getAudienceType()!=null &&
				requestDto.getAudienceType().toLowerCase().equals("all")){
			map.put("audience", "all");
		}else {
			//兼容之前版本
			if (requestDto.getReceiver() != null) {
				List<String> list = new ArrayList<String>();
				list.add(requestDto.getReceiver());
				// list.add("3705");
				tagMap.put("tag", list);
			} else {
				tagMap.put(requestDto.getAudienceType(),
						JSONArray.toList(JSONArray.fromObject(
								requestDto.getReceiverList())));
			}
			map.put("audience", tagMap);
		}

	    Map<Object, Object> msgPlatform = new HashMap<Object, Object>();
	    androidMap.put("alert", requestDto.getContent());
	    androidMap.put("title", requestDto.getContent());
	    androidMap.put("extras", extrasMap);

	    iosMap.put("alert", requestDto.getContent());
	    iosMap.put("sound", "default");
		if(requestDto.getMessageType()!=null&&!requestDto.getMessageType().equals("work_flow")){
			iosMap.put("badge", "+1");
		}
	    iosMap.put("extras", extrasMap);

	    msgPlatform.put("android", androidMap);
	    msgPlatform.put("ios", iosMap);

	    messageMap.put("msg_content", requestDto.getContent());
	    messageMap.put("content_type", "text");
	    messageMap.put("title", requestDto.getContent());

	    options.put("time_to_live", "60");
		if(requestDto.getApnsProduction()!=null &&
				!requestDto.getApnsProduction().toLowerCase().equals("true")){
			options.put("apns_production", "false");
		}else{
			options.put("apns_production", "true");
		}



	    map.put("notification", msgPlatform);
	    map.put("message", messageMap);
	    map.put("options", options);

	    JSONObject jsonOb = JSONObject.fromObject(map);
	    return jsonOb.toString();
	}
}
