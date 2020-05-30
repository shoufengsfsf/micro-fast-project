# 消息队列模块使用说明

## 简介

消息队列模块主要基于rabbitmq实现，在原先的基础上做了一些便捷操作的增强，实现了完全通过yml文件配置创建Exchange、Queue、Binding，减少了声明性的硬编码。

## 使用说明

### 提醒

通过yml生成的队列，默认均为非事务、自动ack队列，如果有业务场景需要做消息队列的事务管理或手动ack，请勿使用

### 1、通过docker-compose启动rabbitmq

```java
version: '3'
services:
  rabbitmq1:
    image: rabbitmq:3-management
    container_name: rabbitmq
    environment:
      - RABBITMQ_DEFAULT_USER=admin
      - RABBITMQ_DEFAULT_PASS=admin
    restart: always
    ports:
      - "15672:15672"
      - "5672:5672"
    logging:
      driver: "json-file"
      options:
        max-size: "200k"
        max-file: "10"
```

### 2、参数说明

```java
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
```

支持的交换机类型

```java
package com.micro.fast.rabbit.constant;

/**
 * 交换机类型
 *
 * @author shoufeng
 */

public enum ExchangeTypeEnum {

    /**
     * Direct 类型的 Exchange 路由规则比较简单，它会把消息路由到那些 binding key 与 routing key 完全匹配的 Queue 中。
     */
    DIRECT_EXCHANGE,

    /**
     * Fanout Exchange 路由规则非常简单，它会把所有发送到该 Exchange 的消息路由到所有与它绑定的 Queue 中。
     */
    FANOUT_EXCHANGE,

    /**
     * Topic Exchange 在匹配规则上进行了扩展，它与 Direct 类型的Exchange 相似，也是将消息路由到 binding key 与 routing key 相匹配的 Queue 中，但这里的匹配规则有些不同，它约定：
     * <p>
     * routing key 为一个句点号 "." 分隔的字符串。我们将被句点号"."分隔开的每一段独立的字符串称为一个单词，例如 "stock.usd.nyse"、"nyse.vmw"、"quick.orange.rabbit"
     * binding key 与 routing key 一样也是句点号 "." 分隔的字符串。
     * binding key 中可以存在两种特殊字符 "*" 与 "#"，用于做模糊匹配。其中 "*" 用于匹配一个单词，"#" 用于匹配多个单词（可以是零个）。
     */
    TOPIC_EXCHANGE,

    /**
     * 性能差，已废弃，不做支持
     * Headers Exchange 不依赖于 routing key 与 binding key 的匹配规则来路由消息，而是根据发送的消息内容中的 headers 属性进行匹配。
     * <p>
     * 在绑定 Queue 与 Exchange 时指定一组 headers 键值对。
     * 当消息发送到 Exchange 时，RabbitMQ 会取到该消息的 headers（也是一个键值对的形式），对比其中的键值对是否完全匹配 Queue 与 Exchange 绑定时指定的键值对；如果完全匹配则消息会路由到该 Queue ，否则不会路由到该 Queue 。
     */
    @Deprecated
    HEADERS_EXCHANGE,
    ;
}
```

支持的队列参数

```java
package com.micro.fast.rabbit.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列参数
 *
 * @author shoufeng
 */

@AllArgsConstructor
public enum QueueArgumentEnum {
    /**
     * 队列参数
     */
    X_MESSAGE_TTL("x-message-ttl", "Number", "1个发布的消息在队列中存在多长时间后被取消（单位毫秒）"),
    X_EXPIRES("x-expires", "Number", "当Queue（队列）在指定的时间未被访问，则队列将被自动删除。"),
    X_MAX_LENGTH("x-max-length", "Number", "队列所能容下消息的最大长度。当超出长度后，新消息将会覆盖最前面的消息，类似于Redis的LRU算法。"),
    X_MAX_LENGTH_BYTES("x-max-length-bytes", "Number", "队列所能容下消息的最大长度。当超出长度后，新消息将会覆盖最前面的消息，类似于Redis的LRU算法。"),
    X_OVERFLOW("x-overflow", "String", "这决定了当达到队列的最大长度时，消息会发生什么。有效值为Drop Head或Reject Publish。"),
    X_DEAD_LETTER_EXCHANGE("x-dead-letter-exchange", "String", "死信交换机，有时候我们希望当队列的消息达到上限后溢出的消息不会被删除掉，而是走到另一个队列中保存起来。"),
    X_DEAD_LETTER_ROUTING_KEY("x-dead-letter-routing-key", "String", "死信路由，如果不定义，则默认为溢出队列的routing-key，因此，一般和x-dead-letter-exchange一起定义。"),
    X_MAX_PRIORITY("x-max-priority", "Number", "队列加上优先级参数"),
    X_QUEUE_MODE("x-queue-mode", "String", "队列类型x-queue-mode=lazy懒队列，在磁盘上尽可能多地保留消息以减少RAM使用；如果未设置，则队列将保留内存缓存以尽可能快地传递消息。"),
    X_QUEUE_MASTER_LOCATOR("x-queue-master-locator", "String", "将队列设置为主位置模式，确定在节点集群上声明时队列主位置所依据的规则。"),
    ;
    /**
     * 键
     */
    @Getter
    private final String key;
    /**
     * 数据类型
     */
    private final String type;
    /**
     * 键描述
     */
    @Getter
    private final String description;

    public static final Map<String, QueueArgumentEnum> KEY_QUEUE_ARGUMENT_ENUM_MAP = new HashMap<String, QueueArgumentEnum>() {
        {
            for (QueueArgumentEnum value : QueueArgumentEnum.values()) {
                put(value.key, value);
            }
        }
    };

}
```

### 3、添加依赖

```java
<dependency>
    <groupId>com.shoufeng.project</groupId>
    <artifactId>micro-fast-mq-rabbit-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 4、通过修改application.yml声明交换机、队列、以及他们的绑定关系

```java
# Tomcat
server:
  tomcat:
    uri-encoding: UTF-8
    max-threads: 1000
    min-spare-threads: 30
  port: 7070

  servlet:
    context-path: /
    session:
      cookie:
        http-only: false

spring:
  application:
    name: fast-demo-mq
  profiles:
    active: dev
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB
      enabled: true
      location: ./data/file

  # RabbitMQ 配置项，对应 RabbitProperties 配置类
  rabbitmq:
    # RabbitMQ 服务的地址
    host: 127.0.0.1
    # RabbitMQ 服务的端口
    port: 5672
    # RabbitMQ 服务的账号
    username: admin
    # RabbitMQ 服务的密码
    password: admin
    template:
      # 对应 RabbitProperties.Retry类
      # =========发送重试=======
      retry:
        enabled: true # 开启发送机制
        max-attempts: 3 # 最大重试次数。默认为 3 。
        initial-interval: 1000 # 重试间隔，单位为毫秒。默认为 1000 。
    listener:
      simple:
        # 对应 RabbitProperties.ListenerRetry 类
        # =========消费重试=======
        retry:
          enabled: true # 开启消费重试机制
          max-attempts: 3 # 最大重试次数。默认为 3 。
          initial-interval: 1000 # 重试间隔，单位为毫秒。默认为 1000 。

micro:
  fast:
    rabbit:
      enable: true
      binding-list:
        - name: bindingName00111
          #DIRECT_EXCHANGE模式交换机routingKey需要完全匹配
          routingKey: demo.direct.routingkey
          queue:
            name: direct_queue_00111
            arguments:
              #队列消息存活5秒
              x-message-ttl: 5000
              #绑定fanout死信交换机
              x-dead-letter-exchange: demo_fanout_exchange_002
              #由于demo绑定的死信交换机是fanout，所以x-dead-letter-routing-key可以不设置
          #              x-dead-letter-routing-key: kkk
          exchange:
            exchangeTypeEnum: DIRECT_EXCHANGE
            name: demo_direct_exchange_001
        - name: bindingName00222
          #FANOUT_EXCHANGE模式的交换机routingKey无意义
          routingKey:
          queue:
            name: fanout_queue_00222
          exchange:
            exchangeTypeEnum: FANOUT_EXCHANGE
            name: demo_fanout_exchange_002
        - name: bindingName00333
          #FANOUT_EXCHANGE模式的交换机routingKey无意义
          routingKey:
          queue:
            name: fanout_queue_00333
          exchange:
            exchangeTypeEnum: FANOUT_EXCHANGE
            name: demo_fanout_exchange_002
```

### 5、创建生产者

```java
package com.micro.fast.demo.mq.service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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
```

### 6、创建消费者

```java
package com.micro.fast.demo.mq.service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author shoufeng
 */

@Component
@RabbitListener(queues = {"fanout_queue_00222"})
public class Demo02Consumer {

    @RabbitHandler
    public void onMessage(String message) {
        System.out.println("Demo02Consumer接受到的消息: " + message);
    }
}
```

```java
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
```

### 7、启动应用

启动时日志会打印yml配置中arguments的参数键、值、以及参数的作用

![启动应用](https://tva1.sinaimg.cn/large/007S8ZIlly1gfau4570sgj31m40eewks.jpg)

### 8、rabbitmq控制台查看配置声明的交换机、队列以及绑定关系是否生成

![image-20200530221839225](https://tva1.sinaimg.cn/large/007S8ZIlly1gfau7smebwj315m0u0q8k.jpg)

![image-20200530222028655](https://tva1.sinaimg.cn/large/007S8ZIlly1gfau9p5y3yj31cq0u00yp.jpg)

### 9、运行Test，模拟生产者生产消息

```java
package com.micro.fast.demo.mq;


import com.micro.fast.demo.mq.service.producer.DemoProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author shoufeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTest {

    @Resource
    private DemoProducer demoProducer;

    @Test
    public void test() {
        demoProducer.sendMessage();
    }
}
```

![image-20200530222246957](https://tva1.sinaimg.cn/large/007S8ZIlly1gfauc39kzyj31880u07a1.jpg)

### 10、消息5秒过期后，查看收费被绑定的死信队列消费

过期消息已不再首次推送的队列上

![image-20200530222431772](https://tva1.sinaimg.cn/large/007S8ZIlly1gfaudwp0dnj315o0u0tea.jpg)

死信队列成功消费（由于示例绑定的是fanout交换机下的两个队列，所以超时消息到死信队列中会被两个队列都消费）

![image-20200530222639464](https://tva1.sinaimg.cn/large/007S8ZIlly1gfaug48v1pj316g0m8dmq.jpg)

### 另外说明

目前yml配置均为手动配置，以后计划引入nacos实现动态配置