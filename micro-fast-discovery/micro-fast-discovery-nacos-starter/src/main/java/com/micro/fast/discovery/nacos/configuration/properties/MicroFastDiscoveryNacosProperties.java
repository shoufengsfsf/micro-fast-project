package com.micro.fast.discovery.nacos.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shoufeng
 */

@Data
@Component
@ConfigurationProperties(prefix = "micro.fast.discovery.nacos")
@NoArgsConstructor
@AllArgsConstructor
public class MicroFastDiscoveryNacosProperties {
    /**
     * 是否开启
     */
    private boolean enable = true;
}
