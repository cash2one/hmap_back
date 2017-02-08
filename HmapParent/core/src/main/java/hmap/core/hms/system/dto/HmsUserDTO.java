package hmap.core.hms.system.dto;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.User;

import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/14.
 */
public class HmsUserDTO extends User{
    private Long roleId;
    private List<Role> Roles;
    public List<Role> getRoles() {
        return Roles;
    }

    public void setRoles(List<Role> roles) {
        Roles = roles;
    }



    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }





}
