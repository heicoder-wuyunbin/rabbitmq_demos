package com.wuyunbin.demo.producers;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@SpringBootTest
public class ProducerTest {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到RabbitMQ队列。
     * 该方法不接受参数并且没有返回值。
     * 主要步骤包括设置消息内容和存活时间（TTL），以及记录发送日志。
     */
    @Test
    public void sendMessage() {
        // 设置消息内容
        String message = "Hello, RabbitMQ!";
        // 设置消息的存活时间（TTL），单位为毫秒
        long ttl = 15000;

        // 记录当前时间并打印发送信息，包括消息内容和TTL
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        log.info("当前时间：{} 发送一条信息给队列{},延时时间{}秒", format.format(new Date()), message, ttl / 1000.0);

        // 使用RabbitTemplate发送消息，并设置消息的TTL
        rabbitTemplate.convertAndSend("test", message, (msg -> {
            // 设置消息存活时间
            msg.getMessageProperties().setExpiration(String.valueOf(ttl));
            return msg;
        }));
    }
}

