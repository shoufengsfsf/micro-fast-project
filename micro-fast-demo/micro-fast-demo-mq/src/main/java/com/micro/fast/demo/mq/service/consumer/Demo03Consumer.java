package com.micro.fast.demo.mq.service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author shoufeng
 */

@Component
@RabbitListener(queues = {"fanout_queue_00333"})
public class Demo03Consumer {

    @RabbitHandler
    public void onMessage(String message) {
        System.out.println("Demo03Consumer接受到的消息: " + message);
    }
}
