/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.websocket Date:2016/11/9 0009 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class WebsocketEndPoint extends TextWebSocketHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final ArrayList<WebSocketSession> users = new ArrayList<>();

    //  @Override
//  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//    users.add(session);
//  }
//
//  @Override
//  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//    super.handleTextMessage(session, message);
//    logger.debug("GOMA === > WebSocketEndPoint.handlerTextMessage...");
//
//    TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
//    session.sendMessage(returnMessage);
//
//  }
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        if (message != null && "OK".equals(message.getPayload())) {
            TextMessage returnMessage = new TextMessage("服务端消息推送开始");
            session.sendMessage(returnMessage);
            int sentMessages = 1;
            while (sentMessages < 4) {
                returnMessage = new TextMessage("来自服务端的第"
                        + sentMessages + "条数据");
                Thread.sleep(3000);
                session.sendMessage(returnMessage);
                sentMessages++;
            }
            returnMessage = new TextMessage("服务端消息推送结束");
            session.sendMessage(returnMessage);
        } else {
            TextMessage returnMessage = new TextMessage("服务端返回消息" + message.getPayload());
            session.sendMessage(returnMessage);
        }
    }
}
