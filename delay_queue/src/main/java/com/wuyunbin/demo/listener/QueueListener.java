package com.wuyunbin.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wuyunbin
 */
@Slf4j
@Component
public class QueueListener {
    public static final String DEAD_QUEUE_NAME = "dead";
    public static final String EXCHANGE_NAME = "exchange";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
    /**
     * 监听死信队列中的消息。
     * <p>
     * 该方法通过@RabbitListener注解绑定到一个特定的队列，该队列与一个交换器关联。
     * 当从死信交换器路由到这个队列的消息到达时，这个方法会被调用。
     * </p>
     *
     * @param message 接收到的消息对象，包含消息体和相关元数据。
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(DEAD_QUEUE_NAME),
            exchange = @Exchange(EXCHANGE_NAME)
    ))
    public void receiveMessage(Message message) {
        // 将消息体从字节转换为字符串
        String msg = new String(message.getBody());
        // 记录接收到的死信队列消息
        log.info("当前时间：{}，收到死信队列的消息：{}", LocalDateTime.now().format(DATE_TIME_FORMATTER), msg);
    }
}
