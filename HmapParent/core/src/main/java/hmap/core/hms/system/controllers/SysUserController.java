/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers Date:2016/8/3 Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.system.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.account.service.IUserRoleService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.security.PasswordManager;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.system.dto.HmsUserDTO;
import hmap.core.hms.feedback.service.IHmsFeedbackService;
import hmap.core.hms.system.service.IHmsUserRoleService;
import hmap.core.search.FeedbackSearchRepository;
import hmap.core.util.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SysUserController extends BaseController {
  private final Logger log = LoggerFactory.getLogger(SysUserController.class);
  @Autowired
  IUserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  PasswordManager passwordManager;
  @Autowired
  IRoleService hmsRoleService;
  @Autowired
  IUserRoleService userRoleService;
  @Autowired
  IHmsUserRoleService iHmsUserRoleService;
  @Autowired
  IHmsFeedbackService iHmsFeedbackService;
  @Autowired
  FeedbackSearchRepository userSearchRepository;

  @RequestMapping(value = "/user", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData createUser(HttpServletRequest request,
      @RequestBody(required = false) HmsUserDTO sysuser) {
    IRequest iRequest = createRequestContext(request);
    String p = sysuser.getPassword();
    if (p == null) {
      p = passwordManager.getDefaultPassword();
    }
    String password = passwordEncoder.encode(p);
    User user = new User();
    user.setUserId(sysuser.getUserId());
    user.setUserName(sysuser.getUserName());
    user.setUserType(sysuser.getUserType());
    user.setEmail(sysuser.getEmail());
    user.setPhone(sysuser.getPhone());
    user.setStartActiveDate(sysuser.getStartActiveDate());
    user.setEndActiveDate(sysuser.getEndActiveDate());
    user.setPassword(password);
    user.setPasswordEncrypted(password);
    user.setStatus(sysuser.getStatus());
    User e = userService.insert(iRequest, user);
    User z = userService.selectByPrimaryKey(iRequest, user);
    UserRole role = new UserRole();
    role.setRoleId(sysuser.getRoleId());
    role.setUserId(z.getUserId());
    iHmsUserRoleService.insert(iRequest, role);
    return new ResponseData(Arrays.asList(e));
  }

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseData getUser(HttpServletRequest request, @PathVariable Long id) {
    IRequest iRequest = createRequestContext(request);
    User e = new User();
    e.setUserId(id);
    User user = userService.selectByPrimaryKey(iRequest, e);
    System.out.println(user.getUserName());
    System.out.println(user.getUserType());
    List<Role> roles = hmsRoleService.selectRolesByUser(iRequest, user);
    HmsUserDTO userDTO = new HmsUserDTO();
    userDTO.setRoleId(roles.get(0).getRoleId());
    userDTO.setPasswordEncrypted(user.getPasswordEncrypted());
    userDTO.setPassword(user.getPassword());
    userDTO.setStartActiveDate(user.getStartActiveDate());
    userDTO.setUserId(user.getUserId());
    userDTO.setEmail(user.getEmail());
    userDTO.setUserType(user.getUserType());
    userDTO.setPhone(user.getPhone());
    userDTO.setEndActiveDate(user.getEndActiveDate());
    userDTO.setStatus(user.getStatus());
    userDTO.setUserName(user.getUserName());
    return new ResponseData(Arrays.asList(userDTO));
  }

  @RequestMapping(value = "/user/queryByName/{name}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseData queryByName(HttpServletRequest request, @PathVariable String name) {
    IRequest iRequest = createRequestContext(request);
    List<User> u = userService.selectListByUserName(name);
    List<HmsUserDTO> l = new ArrayList<HmsUserDTO>();
    if(u != null) {
      for(User user:u) {
        List<Role> roles = hmsRoleService.selectRolesByUser(iRequest, user);
        HmsUserDTO userDTO = new HmsUserDTO();
        userDTO.setRoleId(roles.get(0).getRoleId());
        userDTO.setPasswordEncrypted(user.getPasswordEncrypted());
        userDTO.setPassword(user.getPassword());
        userDTO.setStartActiveDate(user.getStartActiveDate());
        userDTO.setUserId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserType(user.getUserType());
        userDTO.setPhone(user.getPhone());
        userDTO.setEndActiveDate(user.getEndActiveDate());
        userDTO.setStatus(user.getStatus());
        userDTO.setUserName(user.getUserName());
        l.add(userDTO);
      }
    }
    return new ResponseData(l);
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  @ResponseBody
  @Timed
  public ResponseData getAllUser(HttpServletRequest request, PageRequest pr) {
    IRequest iRequest = createRequestContext(request);
    List<User> e = userService.select(iRequest, null, pr.getPage(), pr.getPagesize());
    return new ResponseData(e);
  }


  @RequestMapping(value = "/user", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData updateUser(HttpServletRequest request,
      @RequestBody(required = false) HmsUserDTO sysuser) {
    IRequest iRequest = createRequestContext(request);
    String p = sysuser.getPassword();
    if (p == null) {
      p = passwordManager.getDefaultPassword();
    }
    String password = passwordEncoder.encode(p);
    User user = new User();
    user.setUserId(sysuser.getUserId());
    user.setUserName(sysuser.getUserName());
    user.setUserType(sysuser.getUserType());
    user.setEmail(sysuser.getEmail());
    user.setPhone(sysuser.getPhone());
    user.setStartActiveDate(sysuser.getStartActiveDate());
    user.setEndActiveDate(sysuser.getEndActiveDate());
    user.setPassword(password);
    user.setPasswordEncrypted(password);
    user.setStatus(sysuser.getStatus());
    User e = userService.updateByPrimaryKey(iRequest, user);
    User z = userService.selectByPrimaryKey(iRequest, user);
    // TODO 可能会有空指针 后续需要处理下
    Role currentRole = hmsRoleService.selectRolesByUser(iRequest, z).get(0);
    UserRole userRole =
        iHmsUserRoleService.selectUserRoleByUserIdAndRoleId(z.getUserId(), currentRole.getRoleId());
    userRole.setRoleId(sysuser.getRoleId());
    userRoleService.updateByPrimaryKey(iRequest, userRole);

    return new ResponseData(Arrays.asList(e));
  }

}
