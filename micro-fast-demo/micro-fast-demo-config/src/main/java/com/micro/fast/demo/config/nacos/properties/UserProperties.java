package com.micro.fast.demo.config.nacos.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "micro.fast.user")
@Data
public class UserProperties {
    private String username;
    private Integer age;
}
