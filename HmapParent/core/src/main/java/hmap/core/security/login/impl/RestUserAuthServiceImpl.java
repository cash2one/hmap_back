/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.security 
 * Date:2016/10/12 0012
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.security.login.impl;

import java.util.Map;

public class RestUserAuthServiceImpl extends AbstractUserAuthServiceImpl {
    @Override
    public boolean authenticate(String username, String password, Map<String, String> params) {
        return false;
    }
}
