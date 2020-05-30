package com.micro.fast.demo.mq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author shoufeng
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    // 创建 Queue
//    @Bean
//    public Queue demoQueue() {
//        return new Queue("queue001", // Queue 名字
//                true, // durable: 是否持久化
//                false, // exclusive: 是否排它
//                false); // autoDelete: 是否自动删除
//    }
//
//    // 创建 Direct Exchange
//    @Beans
//    public DirectExchange demoExchange() {
//        return new DirectExchange("exchange001",
//                true,  // durable: 是否持久化
//                false);  // exclusive: 是否排它
//    }
//
//    // 创建 Binding
//    @Bean
//    public Binding demoBinding() {
//        return BindingBuilder.bind(demoQueue()).to(demoExchange()).with("routingkey001");
//    }
}
