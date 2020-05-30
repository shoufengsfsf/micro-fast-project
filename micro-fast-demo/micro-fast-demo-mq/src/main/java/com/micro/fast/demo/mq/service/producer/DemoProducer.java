package com.micro.fast.demo.mq.service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author shoufeng
 */

@Service
public class DemoProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage() {
        rabbitTemplate.convertAndSend("demo_direct_exchange_001", "demo.direct.routingkey", "testetstest");
        System.out.println("demo_direct_exchange_001: testetstest");
    }
}
