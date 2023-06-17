package com.adrainty.im.controller;

import com.adrainty.common.constants.BizErrorConstant;
import com.adrainty.common.exception.MemException;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.im.configuration.WebSocketConfig;
import com.adrainty.im.constants.ChatTypeEnum;
import com.adrainty.im.constants.MsgTypeEnum;
import com.adrainty.im.constants.ReadTypeEnum;
import com.adrainty.im.feign.FileClient;
import com.adrainty.im.feign.SysUserClient;
import com.adrainty.im.utils.RocketMQUtils;
import com.adrainty.module.form.FileShareForm;
import com.adrainty.module.im.MemImMessage;
import com.adrainty.module.sys.SysUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/11 23:48
 */

@ServerEndpoint(value = "/im/websocket", configurator = WebSocketConfig.class)
@Component
@Slf4j
public class MemWebSocketServer {

    private static ApplicationContext context;

    private static final Map<Long, Session> socketMap = new ConcurrentHashMap<>();

    public static void setApplicationContext(ApplicationContext context) {
        MemWebSocketServer.context = context;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        String token = (String) session.getUserProperties().get("token");
        Long userId = JwtUtils.getUserId(token);
        session.getUserProperties().put("userId", userId);
        if (userId < 0) throw new MemException(BizErrorConstant.UN_AUTHORITY_ERROR);
        SysUserClient sysUserClient = context.getBean(SysUserClient.class);
        SysUserDto sysUserDto = sysUserClient.userInfo(userId);
        socketMap.put(userId, session);

        log.info("有新连接加入:" + sysUserDto.getUsername());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        log.info("用户 {} 退出了", session.getUserProperties().get("userId"));
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        String token = (String) session.getUserProperties().get("token");
        Long userId = JwtUtils.getUserId(token);
        SysUserClient sysUserClient = context.getBean(SysUserClient.class);
        SysUserDto sysUserDto = sysUserClient.userInfo(userId);
        String nickname = sysUserDto.getUsername();

        log.info("来自客户端的消息: {}", message);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        MemImMessage imMessage;

        RocketMQUtils rocketMQUtils = context.getBean(RocketMQUtils.class);


        try {
            imMessage = objectMapper.readValue(message, MemImMessage.class);
            imMessage.setCreateTime(new Date());
            imMessage.setUpdateTime(new Date());
            if(ChatTypeEnum.SINGLE_CHAT.getCode().equals(imMessage.getChatType())){
                //单聊.需要找到发送者和接受者.
                Long receiver = imMessage.getReceiver();
                Session toSession = socketMap.get(receiver);
                //发送给接受者.
                if(toSession != null){
                    //发送给发送者
                    toSession.getAsyncRemote().sendText(message);
                }
                rocketMQUtils.sendSingle(imMessage);
                if (MsgTypeEnum.FILE.getCode().equals(imMessage.getMsgType())) {
                    FileShareForm fileShareForm = new FileShareForm();
                    fileShareForm.setSender(imMessage.getSender());
                    fileShareForm.setReceiver(imMessage.getReceiver());
                    fileShareForm.setFileId(Long.parseLong(imMessage.getMessage()));
                    shareFile(fileShareForm);
                }
            } else{
                imMessage.setIsRead(ReadTypeEnum.WAITING.getCode());
                //群发消息
                broadcast(nickname + ": " + imMessage.getMessage());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public void broadcast(String message){
        for (Session ss: socketMap.values()) {
            ss.getAsyncRemote().sendText(message);     //异步发送消息.
        }
    }

    private void shareFile(FileShareForm form) {
        FileClient fileClient = context.getBean(FileClient.class);
        fileClient.share(form);
    }
}
