/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers Date:2016/11/19 0019 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.permission.dto.PermissionDataDTO;
import hmap.core.hms.contact.service.IContactListService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/contactList")
public class ContactListController extends BaseController {
  @Autowired
  IContactListService contactListService;

  @RequestMapping(value = "/search", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public ResponseData searchTag(HttpServletRequest request, @RequestBody JSONObject inbound) {
    List<PermissionDataDTO> permissionList = contactListService.search(inbound.getString("key"));
    return new ResponseData(permissionList);
  }
}
