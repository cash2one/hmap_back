package hmap.core.hms.contact.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.dto.StandardStaffResponseDTO;
import hmap.core.hms.contact.service.IHmsStaffService;
import hmap.core.search.StaffSearchRepository;
import net.sf.json.JSONObject;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class HmsStaffController extends BaseController {

	@Autowired
	private IHmsStaffService staffService;
	@Autowired
	IMessagePublisher messagePublisher;

	@Autowired
	private StaffSearchRepository staffSearchRepository;
//	//参数id，员工工号
//	@Timed
//	@RequestMapping("/api/public/staff/detail")
//	@ResponseBody
//	public ResponseData select(HttpServletRequest request,
//			@RequestBody JSONObject inbound) {
//		IRequest iRequest = createRequestContext(request);
//		List<StandardStaffResponseDTO> list = new ArrayList<StandardStaffResponseDTO>();
//		list.add(staffService.selectStaffDtoByAccountNumber(iRequest,
//				inbound.getString("id")));
//		return new ResponseData(list);
//	}
//	//初始化缓存，第一次使用通讯录前需要调用一次
//	@RequestMapping("/api/public/staff/collect")
//	@ResponseBody
//	@Timed
//	public ResponseData initLucene(HttpServletRequest request) {
//		try {
//			staffService.initLucene();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return new ResponseData();
//	}
	//模糊查询
//	@Timed
//	@RequestMapping("/api/public/staff/query")
//	@ResponseBody
//	public ResponseData selectLucene(HttpServletRequest request,
//			@RequestBody(required = false) QueryParam qp) {
//		List<SimpleStaffResponseDTO> list = new ArrayList<SimpleStaffResponseDTO>();
//		if (qp == null || qp.getKey().trim().equals("")) {
//			return new ResponseData(list);
//		} else {
//			String key = qp.getKey().trim().toLowerCase();
//			key = key.replaceAll(" ", "");
//			int page = qp.getPage();
//			int pageSize = qp.getPageSize();
//			try {
//				list = staffService.search(key, page, pageSize);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return new ResponseData(list);
//		}
//	}
//	//模糊高亮查询
//	@RequestMapping("/api/public/staff/queryHighLight")
//	@ResponseBody
//	public ResponseData selectLuceneHighLight(HttpServletRequest request,
//			@RequestBody(required = false) QueryParam qp) {
//		List<SimpleStaffResponseDTO> list = new ArrayList<SimpleStaffResponseDTO>();
//		if (qp == null || qp.getKey().trim().equals("")) {
//			return new ResponseData(list);
//		} else {
//			String key = qp.getKey().trim().toLowerCase();
//			key = key.replaceAll(" ", "");
//			int page = qp.getPage();
//			int pageSize = qp.getPageSize();
//			try {
//				list = staffService.searchHighLight(key, page, pageSize);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return new ResponseData(list);
//		}
//	}
	//会自动生成拼音
	@Timed
	@RequestMapping("/i/api/staff/insertData")
	@ResponseBody
	public ResponseData insertData(HttpServletRequest request,
			@RequestBody HmsStaff[] staff) {
		IRequest iRequest = createRequestContext(request);
		try {
			staffService.generateData(iRequest, staff);
			return new ResponseData(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(false);
		}
	}


	@Timed
	@RequestMapping("/api/staff/updateData")
	@ResponseBody
	public ResponseData updateData(HttpServletRequest request,
			@RequestBody HmsStaff[] staff) {
		IRequest iRequest = createRequestContext(request);
		try {
			staffService.updateData(iRequest, staff);
			return new ResponseData(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(false);
		}
	}

	@Timed
	@RequestMapping("/api/staff/deleteData")
	@ResponseBody
	public ResponseData deleteData(HttpServletRequest request,
			@RequestBody HmsStaff[] staff) {
		IRequest iRequest = createRequestContext(request);
		try {
			staffService.deleteData(iRequest, staff);
			return new ResponseData(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(false);
		}
	}

	@RequestMapping("/api/staff/detail")
	@ResponseBody
	public ResponseData detail(HttpServletRequest request,
							   @RequestBody JSONObject inbound) {
		IRequest iRequest = createRequestContext(request);
		StandardStaffResponseDTO dto = staffService.selectStaffDtoByAccountNumber(iRequest,inbound.getString("key"));
		return new ResponseData(Arrays.asList(dto));
	}
	@RequestMapping("/api/staff/all")
	@ResponseBody
	public ResponseData all(HttpServletRequest request,
							   @RequestBody JSONObject inbound) {
		IRequest iRequest = createRequestContext(request);
		List<HmsStaff> list = IteratorUtils.toList(staffSearchRepository.findAll().iterator());
		return new ResponseData(list);
	}
	@RequestMapping("/api/staff/init")
	@ResponseBody
	public ResponseData init(HttpServletRequest request,
							@RequestBody JSONObject inbound) {
		IRequest iRequest = createRequestContext(request);
		staffSearchRepository.save(staffService.selectAll());
		return new ResponseData();
	}
	@RequestMapping("/api/staff/query")
	@ResponseBody
	public ResponseData query(HttpServletRequest request,
							  @RequestBody JSONObject inbound) {
		IRequest iRequest = createRequestContext(request);
		return new ResponseData(staffSearchRepository.findSelective(inbound.getString("key")));
	}





}
