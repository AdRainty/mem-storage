package com.adrainty.im.handler;

import com.adrainty.common.exception.MemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/11 23:53
 */

@Slf4j
@SuppressWarnings("NullableProblems")
public class MemWebSocketHandler implements WebSocketHandler {

    //保存用户会话信息，用于服务端群发
    private static final ConcurrentLinkedDeque<WebSocketSession> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();

    //保存当前会话用户名称
    private String username;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.username = (String) session.getAttributes().get("username");
        concurrentLinkedDeque.add(session);
        log.info("用户 {} 连接", username);
        sendMessage(new TextMessage(username + "加入聊天室"));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        sendMessage(message);
    }

    public void sendMessage(WebSocketMessage<?> message) {
        concurrentLinkedDeque.forEach(item -> {
            try {
                if (item.isOpen()) {
                    item.sendMessage(message);
                }
            } catch (Exception e) {
                throw new MemException("发送消息异常");
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        // 
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)  {
        sendMessage(new TextMessage(username + "退出聊天室"));
        concurrentLinkedDeque.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
