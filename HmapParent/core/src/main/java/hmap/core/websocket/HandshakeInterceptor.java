/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.websocket Date:2016/11/9 0009 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
  private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    logger.debug("GOMA ===> Before Handshake");
    return super.beforeHandshake(request, response, wsHandler, attributes);
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Exception ex) {
    logger.debug("GOMA ===> After Handshake");
    super.afterHandshake(request, response, wsHandler, ex);
  }
}
