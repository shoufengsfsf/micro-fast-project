package com.micro.fast.log.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 日志配置参数
 *
 * @author shoufeng
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "micro.fast.log")
public class MicroFastLogProperties {

    /**
     * 是否开启系统日志（默认关闭）
     */
    private boolean enable = false;

    /**
     * token请求头名称
     */
    private String tokenHeader = "accessToken";

    /**
     * 拦截器优先级
     */
    private Integer interceptorOrder = -10;

}
