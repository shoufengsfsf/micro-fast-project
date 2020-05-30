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
