package com.micro.fast.rabbit.configuration.properties;

import com.micro.fast.rabbit.constant.ExchangeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置值
 *
 * @author shoufeng
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "micro.fast.rabbit")
public class MicroFastRabbitMqProperties {

    private boolean enable = false;

    private List<Binding> bindingList;

    /**
     * 绑定
     */
    @Data
    public static class Binding {
        /**
         * 绑定名称
         */
        private String name;
        /**
         * 交换机
         */
        private Exchange exchange;
        /**
         * 队列
         */
        private Queue queue;
        /**
         * 路由键
         */
        private String routingKey;
    }

    /**
     * 交换机
     */
    @Data
    public static class Exchange {
        /**
         * 交换机类型
         */
        private ExchangeTypeEnum exchangeTypeEnum;
        /**
         * 交换机名称
         */
        private String name;
        /**
         * durable: 是否持久化
         */
        private boolean durable = true;
        /**
         * autoDelete: 是否自动删除
         */
        private boolean autoDelete = false;
        /**
         * 参数
         */
        private Map<String, Object> arguments;
    }

    /**
     * 队列
     */
    @Data
    public static class Queue {
        /**
         * Queue 名字
         */
        private String name;
        /**
         * durable: 是否持久化
         */
        private boolean durable = true;
        /**
         * exclusive: 是否排它
         */
        private boolean exclusive = false;
        /**
         * autoDelete: 是否自动删除
         */
        private boolean autoDelete = false;
        /**
         * 参数
         */
        private Map<String, Object> arguments = new HashMap<>();
    }
}
