/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.util Date:2016/8/29 0029 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.util;


import com.hand.hap.core.IRequest;
import com.hand.hap.core.IRequestListener;
import com.hand.hap.core.impl.DefaultRequestListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public final class StatelessRequestHelper {
  private static ThreadLocal<IRequest> localRequestContext = new ThreadLocal();
  private static IRequestListener requestListener = new DefaultRequestListener();

  public StatelessRequestHelper() {}

  public IRequestListener getRequestListener() {
    return requestListener;
  }

  public void setRequestListener(IRequestListener requestListener) {
    requestListener = requestListener;
  }

  public static IRequest newEmptyRequest() {
    return requestListener.newInstance();
  }

  public static void setCurrentRequest(IRequest request) {
    localRequestContext.set(request);
  }

  public static void clearCurrentRequest() {
    localRequestContext.remove();
  }

  public static IRequest getCurrentRequest() {
    return getCurrentRequest(false);
  }

  public static IRequest getCurrentRequest(boolean returnEmptyForNull) {
    IRequest request = (IRequest) localRequestContext.get();
    return request == null && returnEmptyForNull ? newEmptyRequest() : request;
  }

  public static IRequest createServiceRequest(HttpServletRequest httpServletRequest) {
    IRequest requestContext = requestListener.newInstance();
    HttpSession session = httpServletRequest.getSession(false);
    // if (session != null) {
    // requestContext.setUserId((Long) session.getAttribute("userId"));
    // requestContext.setRoleId((Long) session.getAttribute("roleId"));
    // Locale mdcMap = RequestContextUtils.getLocale(httpServletRequest);
    // if (mdcMap != null) {
    // requestContext.setLocale(mdcMap.toString());
    // }
    // }
    requestContext.setLocale("zh_CN");// 先默认登录语言是中文
    Map<String, String> mdcMap1 = MDC.getCopyOfContextMap();
    if (mdcMap1 != null) {
      mdcMap1.forEach((k, v) -> {
        requestContext.setAttribute("MDC.".concat(k), v);
      });
    }

    requestListener.afterInitialize(httpServletRequest, requestContext);
    return requestContext;
  }

  public static String getIp() {
    String localip = null;// 本地IP，如果没有配置外网IP则返回它
    String netip = null;// 外网IP
    try {
      Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
      InetAddress ip = null;
      boolean finded = false;// 是否找到外网IP
      while (netInterfaces.hasMoreElements() && !finded) {
        NetworkInterface ni = netInterfaces.nextElement();
        Enumeration<InetAddress> address = ni.getInetAddresses();
        while (address.hasMoreElements()) {
          ip = address.nextElement();
          // System.out.println(ni.getName() + ";" + ip.getHostAddress()
          // + ";ip.isSiteLocalAddress()="
          // + ip.isSiteLocalAddress()
          // + ";ip.isLoopbackAddress()="
          // + ip.isLoopbackAddress());
          if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
              && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
            netip = ip.getHostAddress();
            finded = true;
            break;
          } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
              && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
            localip = ip.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
    if (netip != null && !"".equals(netip)) {
      return netip;
    } else {
      return localip;
    }
  }

  public static String getCurrentRequestRemoteAddr() {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String realIp = request.getHeader("x-forwarded-for");
    if (StringUtils.isEmpty(realIp)) {
      realIp = request.getRemoteAddr();
    }
    else {
      if (realIp != null && realIp.indexOf(",") != -1) {
        realIp = realIp.substring(0,realIp.indexOf(",")).trim();
      }
    }
    return realIp;
  }
}
