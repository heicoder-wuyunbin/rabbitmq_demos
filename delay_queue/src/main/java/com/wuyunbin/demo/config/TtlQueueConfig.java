package com.wuyunbin.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyunbin
 */
@Configuration
public class TtlQueueConfig {
    public static final String TEST_QUEUE_NAME = "test";
    public static final String DEAD_QUEUE_NAME = "dead";

    /**
     * 创建并返回一个队列配置，该配置适用于测试场景。
     * 队列具有以下特点：
     * - 是持久化的，确保在服务器重启后仍然存在。
     * - 配置了死信交换（Dead Letter Exchange），当消息在队列中无法被正确处理时，会被发送到指定的死信队列。
     *
     * @return Queue 返回配置好的队列对象。
     */
    @Bean
    public Queue testQueue() {
        // 创建一个测试队列，配置了死信交换和路由键
        return QueueBuilder.durable(TEST_QUEUE_NAME)
                .deadLetterExchange("")
                .deadLetterRoutingKey(DEAD_QUEUE_NAME)
                .build();
    }

}