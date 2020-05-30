//package com.micro.fast.demo.mq.service.consumer;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author shoufeng
// */
//
//@Component
//@RabbitListener(queues = {"direct_queue_00111"})
//public class Demo04Consumer {
//
//    @RabbitHandler
//    public void onMessage(String message) {
//        System.out.println("Demo04Consumer接受到的消息: " + message);
//    }
//}
