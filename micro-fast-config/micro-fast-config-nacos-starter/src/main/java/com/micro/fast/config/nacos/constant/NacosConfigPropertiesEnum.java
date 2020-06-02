package com.micro.fast.config.nacos.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shoufeng
 */

@AllArgsConstructor
public enum NacosConfigPropertiesEnum {

    /**
     * s
     * nacos配置参数解释
     */
    SERVER_ADDR("nacos的Config服务器地址", "serverAddr", "nacos的Configs服务器地址"),
    USERNAME("nacos认证用户名", "username", "nacos认证用户名"),
    PASSWORD("nacos认证密码", "password", "nacos认证密码"),
    ENCODE("配置内容编码", "encode", "配置内容编码"),
    GROUP("nacos的组名", "group", "nacos的组名，默认为DEFAULT_GROUP"),
    PREFIX("dataId前缀", "prefix", "dataId前缀"),
    FILE_EXTENSION("文件拓展名", "fileExtension", "nacos配置dataId的后缀，也是配置内容的文件扩展名，默认properties"),
    TIMEOUT("获取配置超时时间", "timeout", "获取配置超时时间"),
    MAX_RETRY("服务器最大重连次数", "maxRetry", "服务器最大重连次数"),
    CONFIG_LONG_POLL_TIMEOUT("nacos获得配置长轮询超时", "configLongPollTimeout", "nacos获得配置长轮询超时"),
    CONFIG_RETRY_TIME("nacos获得配置失败的重试时间", "configRetryTime", "nacos获得配置失败的重试时间"),
    ENABLE_REMOTE_SYNC_CONFIG("启用远程同步配置", "enableRemoteSyncConfig", "启用远程同步配置，默认false"),
    ENDPOINT("服务的域名", "endpoint", "服务的域名，通过它可以动态地获取服务器地址"),
    NAMESPACE("命名空间", "endpoint", "命名空间，用于分离注册不同环境"),
    ACCESS_KEY("名称空间的accessKey", "accessKey", "名称空间的accessKey"),
    SECRET_KEY("名称空间的secretKey", "secretKey", "名称空间的secretKey"),
    CONTEXT_PATH("nacos配置服务器的上下文路径", "contextPath", "nacos配置服务器的上下文路径"),
    CLUSTER_NAME("配置集群名", "clusterName", "配置集群名"),
    NAME("dataId名称", "name", "dataId名称"),
    REFRESH_ENABLED("是否刷新配置", "refreshEnabled", "是否刷新配置，默认是"),

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

    public static final Map<String, NacosConfigPropertiesEnum> NACOS_CONFIG_PROPERTIES_ENUM_MAP = new HashMap<String, NacosConfigPropertiesEnum>() {
        {
            for (NacosConfigPropertiesEnum nacosConfigPropertiesEnum : NacosConfigPropertiesEnum.values()) {
                put(nacosConfigPropertiesEnum.getEnglishName(), nacosConfigPropertiesEnum);
            }
        }
    };

}
