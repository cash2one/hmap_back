package hmap.core.security.login.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.hand.hap.security.PasswordManager;
import hmap.core.security.login.IClientDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.security.CustomUserDetails;

import javax.annotation.Resource;

/**
 * Created by hailor on 16/6/12.
 */
class HmapUserDetailsService implements IClientDetailService {

    Logger logger = LoggerFactory.getLogger(HmapUserDetailsService.class);

    @Autowired
    private IUserService userService;
    //UserNamePassword
    @Override
    public UserDetails loadUserByUsername(String username,String password) throws UsernameNotFoundException {
        User user = userService.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//如果是中台用户默认都是管理员
        UserDetails userDetails = new CustomUserDetails(user.getUserId(), user.getUserName(),
                user.getPasswordEncrypted(), true, true, true, true, authorities);
        return userDetails;
    }

}
