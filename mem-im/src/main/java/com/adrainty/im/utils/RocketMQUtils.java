package com.adrainty.im.utils;

import com.adrainty.im.constants.RocketMQConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * RocketMQ工具类
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/13 23:41
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RocketMQUtils {

    private final RocketMQTemplate rocketMQTemplate;

    private static final String TOPIC = RocketMQConstant.TOPIC;

    /**
     * 发送消息
     * @param obj 消息对象
     * @param <T> 消息泛型
     */
    public <T> void sendSync(T obj) {
        rocketMQTemplate.convertAndSend(TOPIC, obj);
    }

    /**
     * 发送带Tag的消息
     * @param tag 标签
     * @param obj 消息对象
     * @param <T> 消息泛型
     * @return 发送结果
     */
    public <T> SendResult sendSync(String tag, T obj) {
        return rocketMQTemplate.syncSend(TOPIC + ":" + tag, MessageBuilder.withPayload(obj).build());
    }

    /**
     * 发送异步消息
     * @param message 待发送的消息对象
     * @param sendCallback 回调函数
     */
    public void sendAsync(Message<?> message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(TOPIC, message, sendCallback);
    }

    /**
     * 发送异步消息
     * @param tag 标签
     * @param message 待发送的消息对象
     * @param sendCallback 回调函数
     */
    public void sendAsync(String tag, Message<?> message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(TOPIC + ":" + tag, message, sendCallback);
    }

    /**
     * 发送单向消息
     * @param obj 消息对象
     * @param <T> 泛型
     */
    public <T> void sendSingle(T obj) {
        Message<T> message = MessageBuilder.withPayload(obj).build();
        rocketMQTemplate.sendOneWay(TOPIC, message);
    }

}
