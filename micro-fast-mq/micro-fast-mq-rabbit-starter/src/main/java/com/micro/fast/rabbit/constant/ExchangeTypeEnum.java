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
