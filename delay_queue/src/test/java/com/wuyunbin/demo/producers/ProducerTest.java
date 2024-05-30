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

    @Test
    public void sendMessage() {
        String message = "Hello, RabbitMQ!";
        long ttl = 15000;
        // 获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        log.info("当前时间：{} 发送一条信息给队列{},延时时间{}秒", format.format(new Date()), message, ttl / 1000.0);

        rabbitTemplate.convertAndSend("test", message, (msg -> {
            // 发送消息并设置TTL
            msg.getMessageProperties().setExpiration(String.valueOf(ttl));
            return msg;
        }));
    }
}
