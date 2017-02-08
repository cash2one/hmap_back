package hmap.core.hms.contact.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.contact.domain.HmsDept;
import hmap.core.hms.dto.DeptQueryDTO;
import hmap.core.hms.contact.service.IHmsDeptService;
import hmap.core.security.SecurityUtils;
import hmap.core.util.StatelessRequestHelper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/dept")
public class HmsDeptController extends BaseController {
  @Autowired
  private IHmsDeptService deptService;

  // 插入部门数据
  @Timed
  @RequestMapping("/insertData")
  @ResponseBody
  public ResponseData insertData(HttpServletRequest request, @RequestBody HmsDept[] dept) {
    IRequest iRequest = createRequestContext(request);
    try {
      deptService.generateData(iRequest, dept);
      return new ResponseData(true);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData(false);
    }

  }

  // 修改部门数据
  @Timed
  @RequestMapping("/updateData")
  @ResponseBody
  public ResponseData updateData(HttpServletRequest request, @RequestBody HmsDept[] dept) {
    IRequest iRequest = createRequestContext(request);
    try {
      deptService.updateData(iRequest, dept);
      return new ResponseData(true);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData(false);
    }
  }

  // 删除部门数据
  @Timed
  @RequestMapping("/deleteData")
  @ResponseBody
  public ResponseData deleteData(HttpServletRequest request, @RequestBody HmsDept[] dept) {
    IRequest iRequest = createRequestContext(request);
    try {
      deptService.deleteData(iRequest, dept);
      return new ResponseData(true);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData(false);
    }
  }

  // 生成部门树, 需要传入参数id ，此id为部门数的根节点——即最上层部门的部门id
  @RequestMapping("/getTree")
  @ResponseBody
  public ResponseData getTree(HttpServletRequest request, @RequestBody JSONObject inbound) {
    try {
      deptService.completeTree(deptService.generateTree(Long.parseLong(inbound.getString("id"))));
      return new ResponseData(true);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData(false);
    }
  }

  // 通过部门号来获取部门详情，其中包含改部门级别的员工和下属部门的宗员工数，还包括直属下级部门简要信息，这里的rootId是指最上层根节点的id
  @Timed
  @RequestMapping("/getDetail")
  @ResponseBody
  public ResponseData getDetail(HttpServletRequest request, @RequestBody JSONObject inbound) {
    Long deptNo = Long.parseLong(inbound.getString("id"));
    Long rootId = Long.parseLong(inbound.getString("rootId"));
    return new ResponseData(Arrays.asList(deptService.getDetail(deptNo, rootId)));
  }

  // 通过token信息来获取当前登陆人的组织信息,这里的rootId是指最上层根节点的id
  @Timed
  @RequestMapping("/getStaffDeptInfo")
  @ResponseBody
  public ResponseData getStaffDeptInfo(HttpServletRequest request,
      @RequestBody JSONObject inbound) {
    String empNo = SecurityUtils.getCurrentUserLogin();
    Long rootId = Long.parseLong(inbound.getString("rootId"));
    return new ResponseData(Arrays.asList(deptService.getStaffDeptInfo(empNo, rootId)));
  }

  @Timed
  @RequestMapping("/getAllDept")
  @ResponseBody
  public ResponseData getTreeWithoutStaff(HttpServletRequest request) {
    return new ResponseData(deptService.selectAllDept());
  }

  @Timed
  @RequestMapping("/updateAllDept")
  @ResponseBody
  public ResponseData updateAllDept(HttpServletRequest request) {
    List<HmsDept> allDept=deptService.selectAll();
    IRequest iRequest= StatelessRequestHelper.createServiceRequest(request);
    HmsDept[] deptArray=allDept.toArray(new HmsDept[allDept.size()]);
    deptService.updateData(iRequest,deptArray);
    return new ResponseData(deptService.selectAll());
  }
  @Timed
  @RequestMapping(value="/getDeptStaff",produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseData getDeptStaff(HttpServletRequest request, @RequestBody DeptQueryDTO dept) {
    Long deptNo = dept.getDeptNumber();
    return new ResponseData(deptService.getStaffByDept(deptNo,dept.getPage(),dept.getPagesize()));
  }
}
