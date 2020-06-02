package com.micro.fast.discovery.nacos.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shoufeng
 */

@AllArgsConstructor
public enum NacosDiscoveryPropertiesEnum {

    /**
     * nacos配置参数解释
     */
    SERVER_ADDR("nacos的Discovery服务器地址", "serverAddr", "nacos的Discovery服务器地址"),
    USERNAME("nacos认证用户名", "username", "nacos认证用户名"),
    PASSWORD("nacos认证密码", "password", "nacos认证密码"),
    ENDPOINT("服务的域名", "endpoint", "服务的域名，通过它可以动态地获取服务器地址"),
    NAMESPACE("命名空间", "endpoint", "命名空间，用于分离注册不同环境"),
    WATCH_DELAY("观察延迟", "watchDelay", "观察延迟，从nacos服务器获取新服务的延迟时间默认30000毫秒"),
    LOG_NAME("日志名称", "logName", "nacos命名日志文件名"),
    SERVICE("服务名称", "service", "用于注册的服务名称"),
    WEIGHT("服务实例的权重", "weight", "服务实例的权重，值越大，权重越大默认为1"),
    CLUSTER_NAME("nacos的集群名称", "clusterName", "nacos的集群名称，默认为DEFAULT"),
    GROUP("nacos的组名", "group", "nacos的组名，默认为DEFAULT_GROUP"),
    NAMING_LOAD_CACHE_AT_START("在启动时命名负载缓存", "namingLoadCacheAtStart", "在应用程序启动时从本地缓存命名负载默认为false"),
    REGISTER_ENABLED("是否注册", "registerEnabled", "如果您只想订阅，但不想注册您的服务，请将其设置为false，默认true"),
    IP("服务实例ip", "ip", "如果自动检测ip工作正常，您想注册的服务实例的ip地址不需要设置"),
    NETWORK_INTERFACE("网络接口", "networkInterface", "要注册哪个网络接口的ip，默认\"\""),
    PORT("服务实例端口", "port", "如果自动检测端口工作正常，您想为服务实例注册的端口不需要设置"),
    SECURE("是否是https服务", "secure", "您的服务是否是https服务，默认false"),
    ACCESS_KEY("名称空间的accessKey", "accessKey", "名称空间的accessKey"),
    SECRET_KEY("名称空间的secretKey", "secretKey", "名称空间的secretKey"),
    HEART_BEAT_INTERVAL("心跳间隔", "heartBeatInterval", "心跳间隔，时间单位:秒"),
    HEART_BEAT_TIMEOUT("心跳超时", "heartBeatTimeout", "心跳超时时间单位:秒"),
    IP_DELETE_TIMEOUT("ip删除超时时间", "ipDeleteTimeout", "ip删除超时时间时间单位:秒"),
    ;

    /**
     * 中文名
     */
    @Getter
    private final String chineseName;

    /**
     * 英文名
     */
    @Getter
    private final String englishName;


    /**
     * 描述
     */
    @Getter
    private final String description;

    public static final Map<String, NacosDiscoveryPropertiesEnum> NACOS_DISCOVERY_PROPERTIES_ENUM_MAP = new HashMap<String, NacosDiscoveryPropertiesEnum>() {
        {
            for (NacosDiscoveryPropertiesEnum nacosDiscoveryPropertiesEnum : NacosDiscoveryPropertiesEnum.values()) {
                put(nacosDiscoveryPropertiesEnum.getEnglishName(), nacosDiscoveryPropertiesEnum);
            }
        }
    };
}
