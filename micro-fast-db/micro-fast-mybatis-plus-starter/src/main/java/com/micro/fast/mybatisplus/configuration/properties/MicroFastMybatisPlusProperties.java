package com.micro.fast.mybatisplus.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置参数
 *
 * @author shoufeng
 */

@Data
@Component
@ConfigurationProperties(prefix = "micro.fast.mybatisplus")
@NoArgsConstructor
@AllArgsConstructor
public class MicroFastMybatisPlusProperties {

    /**
     * 是否开启
     */
    private boolean enable = false;

}
