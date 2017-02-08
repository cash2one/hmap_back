package hmap.core.security.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IClientDetailService {
    UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException;
}
