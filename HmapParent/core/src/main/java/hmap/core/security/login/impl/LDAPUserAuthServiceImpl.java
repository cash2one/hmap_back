/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.security Date:2016/10/12 0012 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.security.login.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import java.util.Map;

public class LDAPUserAuthServiceImpl extends AbstractUserAuthServiceImpl {

  Logger logger = LoggerFactory.getLogger(LDAPUserAuthServiceImpl.class);
  @Autowired
  LdapTemplate ldapTemplate;

  public boolean authenticate(String username, String password,Map<String,String> params) {
    AndFilter filter = new AndFilter();
    filter.and(new EqualsFilter("ObjectClass", "person")).and(new EqualsFilter("employeeType", "0"))
        .and(new EqualsFilter("employeeNumber", username));
    logger.info("login filter is:{}",filter.toString());
    boolean result =
        ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
    return result;
  }

}
