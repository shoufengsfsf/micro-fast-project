package com.micro.fast.minio.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * minio配置参数
 *
 * @author shoufeng
 */

@Data
@Component
@ConfigurationProperties(prefix = "micro.fast.minio")
@NoArgsConstructor
@AllArgsConstructor
public class MicroFastMinioClientProperties {

    /**
     * 是否开启
     */
    private boolean enable = false;

    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * Access key就像用户ID，可以唯一标识你的账户
     */
    private String accessKey;

    /**
     * Secret key是你账户的密码
     */
    private String secretKey;

}
