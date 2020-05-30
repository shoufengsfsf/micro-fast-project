package com.micro.fast.demo.mq.service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author shoufeng
 */

@Component
@RabbitListener(queues = {"queue00111"})
public class Demo01Consumer {

    @RabbitHandler
    public void onMessage(String message) {
        System.out.println("queue00111接受到的消息: " + message);
    }
}
