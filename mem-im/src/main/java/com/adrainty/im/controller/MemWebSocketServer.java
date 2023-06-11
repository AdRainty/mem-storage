package com.adrainty.im.controller;

import com.adrainty.im.constants.ChatTypeEnum;
import com.adrainty.module.im.MemImMessage;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/11 23:48
 */

@ServerEndpoint(value = "/im/websocket/{nickname}")
@Component
@Slf4j
public class MemWebSocketServer {

    //用来存放每个客户端对应的MyWebSocket对象。
    private static final CopyOnWriteArraySet<MemWebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static final Map<Long, Session> map = new ConcurrentHashMap<>();
    
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nickname") String nickname) {
        this.session = session;

        map.put(Long.valueOf(session.getId()), session);

        webSocketSet.add(this);     //加入set中
        log.info("有新连接加入:" + nickname + ",当前在线人数为" + webSocketSet.size());
        this.session.getAsyncRemote().sendText("恭喜" + nickname + "成功连接上WebSocket(其频道号：" + session.getId() + ")-->当前在线人数为：" + webSocketSet.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        log.info("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickname") String nickname) {
        log.info("来自客户端的消息-->"+nickname+": " + message);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        MemImMessage imMessage;

        try {
            imMessage = objectMapper.readValue(message, MemImMessage.class);
            if(ChatTypeEnum.SINGLE_CHAT.getCode().equals(imMessage.getChatType())){
                //单聊.需要找到发送者和接受者.

                imMessage.setSender(Long.valueOf(session.getId())); //发送者
                Long sender = imMessage.getSender();
                Long receiver = imMessage.getReceiver();
                Session fromSession = map.get(sender);
                Session toSession = map.get(receiver);
                //发送给接受者.
                if(toSession != null){
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText(nickname+"："+imMessage.getMessage());
                    toSession.getAsyncRemote().sendText(nickname+"："+imMessage.getMessage());
                }else{
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");
                }
            }else{
                //群发消息
                broadcast(nickname+": "+imMessage.getMessage());
            }
        } catch (JsonParseException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }
    /**
     * 群发自定义消息
     * */
    public  void broadcast(String message){
        for (MemWebSocketServer item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }
}
