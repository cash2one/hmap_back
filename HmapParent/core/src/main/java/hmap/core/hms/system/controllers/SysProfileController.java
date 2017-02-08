package hmap.core.hms.system.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.Profile;
import com.hand.hap.system.dto.ProfileValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.mapper.ProfileValueMapper;
import com.hand.hap.system.service.IProfileService;
import hmap.core.util.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2016/9/1.
 */
@Controller
@RequestMapping("/api")
public class SysProfileController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    IProfileService profileService;
    @Autowired
    private ProfileValueMapper profileValueMapper;

    @RequestMapping(value="/profile", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData createProfile(HttpServletRequest request, @RequestBody(required = false) Profile profile) {
        IRequest iRequest = createRequestContext(request);
        Profile e = profileService.createProfile(iRequest,profile);
        return new ResponseData(Arrays.asList(e));
    }

    @RequestMapping(value="/profile", method = RequestMethod.GET)
    @ResponseBody
    @Timed
    public ResponseData getAllProfile(HttpServletRequest request, PageRequest pr){
        IRequest iRequest = createRequestContext(request);
            List<Profile> e=  profileService.selectProfiles(null, pr.getPage(), pr.getPagesize());
        return new ResponseData(e);
    }

    @RequestMapping(value="/profile", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData updateProfile(HttpServletRequest request, @RequestBody(required = false) Profile profile) {
        IRequest iRequest = createRequestContext(request);
        Profile e = profileService.updateProfile(iRequest,profile);
        return new ResponseData(Arrays.asList(e));
    }

    @RequestMapping(value="/profileValue", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData createProfileValue(HttpServletRequest request, @RequestBody(required = false) ProfileValue profileValue) {
        IRequest iRequest = createRequestContext(request);
        profileValueMapper.insertSelective(profileValue);
        return new ResponseData();
    }


    @RequestMapping(value="/profileValue", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData updateProfileValue(HttpServletRequest request, @RequestBody(required = false) ProfileValue profileValue) {
        IRequest iRequest = createRequestContext(request);
        profileValueMapper.updateByPrimaryKeySelective(profileValue);
        return new ResponseData();
    }

    @RequestMapping(value="/profileValue", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseData getAllProfileValue(HttpServletRequest request, ProfileValue profileValue,PageRequest pr) {
        IRequest iRequest = createRequestContext(request);
        Profile profile = new Profile();
        List<Profile> profiles = null;
        if(profileValue.getProfileId() != null) {
            profile.setProfileId(profileValue.getProfileId());
            profiles = profileService.selectProfiles(profile, pr.getPage(), pr.getPagesize());
        }
        List<ProfileValue> e = profileService.selectProfileValues(profileValue);
        List<ProfileValue> levels = null;
        if(profileValue.getLevelId()!=null) {
            levels =  profileService.selectLevelValues(profileValue.getLevelId(),pr.getPage(), pr.getPagesize());
        }
        List<Object> list = new ArrayList<Object>();
        list.add(e);
        list.add(levels);
        list.add(profiles);
        return new ResponseData(Arrays.asList(list));
    }




}
