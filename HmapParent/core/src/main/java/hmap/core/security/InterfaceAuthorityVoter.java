package hmap.core.security;

import com.hand.hap.cache.Cache;
import com.hand.hap.cache.impl.CacheManagerImpl;
import hmap.core.hms.api.dto.HmsInterfaceAuthDTO;
import hmap.core.hms.api.service.IHmsInterfaceAuthService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Created by Koma.Tshu on 16/9/8.
 */
public class InterfaceAuthorityVoter implements AccessDecisionVoter<FilterInvocation> {
    private static final Logger logger = LoggerFactory.getLogger(InterfaceAuthorityVoter.class);
    private static final String INTERNAL_SYSTEM_NAME = "HMAP";
    @Autowired
    private IHmsInterfaceAuthService hmsInterfaceAuthService;
    @Autowired
    private CacheManagerImpl cml;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Cache<HmsInterfaceAuthDTO> getCache() {
        return cml.getCache("interfaceAuth");
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation fi,
                    Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;
        assert authentication != null;
        assert fi != null;
        assert attributes != null;
        HttpServletRequest request = fi.getRequest();

        String uri = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
        logger.info("uri:::::" + uri);

        // clientId
        String clientId = null;
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oa = (OAuth2Authentication) authentication;
            OAuth2Request or = oa.getOAuth2Request();
            clientId = or.getClientId();
            Collection<GrantedAuthority> authorities=oa.getAuthorities();
            for(GrantedAuthority authority:authorities){
                //如果是中台用户，直接返回
               if(authority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")){
                   return ACCESS_GRANTED;
               }
            }
        }

        boolean internalInterfaceAuthorized = false;
        boolean remoteInterfaceAuthorized = false;
        boolean other = false;
        if (uri.startsWith("/i/api")) {// 若为内部接口
            // 从缓存中将内部地址维护的权限取出String key=INTERFACE_AUTH+internalURI
            // 先取出行缓存数据，如果查询出来则直接返回
            // 如果没有行数据，再去兆找头，如果能找到则表示维护了权限，如果还是没找到的表示无权限
            HmsInterfaceAuthDTO auth =
                    hmsInterfaceAuthService.getAuthByAppidAndApiUrl(clientId, INTERNAL_SYSTEM_NAME, uri);
            if (auth != null) {
                internalInterfaceAuthorized = true;
            } else {
                auth = hmsInterfaceAuthService.getAuthByAppidAndSystemName(clientId, INTERNAL_SYSTEM_NAME);
                if (auth != null) {
                    internalInterfaceAuthorized = true;
                }
            }

        } else if (uri.startsWith("/r/api")) {// 若为透传外部接口
            // 从缓存中将透传地址维护的权限取出String key=INTERFACE_AUTH+sysName+apiName
            // 先取出行缓存数据，如果查询出来则直接返回
            // 如果没有行数据，再去兆找头，如果能找到则表示维护了权限，如果还是没找到的表示无权限
            String sysName = request.getParameter("sysName");
            String apiName = request.getParameter("apiName");
            HmsInterfaceAuthDTO auth =
                    hmsInterfaceAuthService.getAuthByAppidAndApiName(clientId, sysName, apiName);
            if (auth != null) {
                remoteInterfaceAuthorized = true;
            } else {
                auth = hmsInterfaceAuthService.getAuthByAppidAndSystemName(clientId, sysName);
                if (auth != null) {
                    remoteInterfaceAuthorized = true;
                }
            }
        } else {
            other = true;
        }
        if (internalInterfaceAuthorized || remoteInterfaceAuthorized || other) {
            result = ACCESS_GRANTED;
        } else {
            result = ACCESS_DENIED;
        }
        return result;
    }
}
