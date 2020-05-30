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

    @PostConstruct
    public void sendMessage() {
        rabbitTemplate.convertAndSend("exchange00111", "kkk", "exchange00111testetstest");
        System.out.println("exchange00111发送消息: testetstest");
        rabbitTemplate.convertAndSend("exchange00222", null, "exchange00222testetstest");
        System.out.println("exchange00222发送消息: testetstest");
    }
}
