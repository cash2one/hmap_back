package hmap.core.security.login.impl;

import com.hand.hap.account.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DBUserAuthServiceImpl extends AbstractUserAuthServiceImpl {

  private Logger logger = LoggerFactory.getLogger(DBUserAuthServiceImpl.class);
  @Override
  public boolean authenticate(String username, String password, Map<String, String> params) {
    return false;
  }

}
