package com.micro.fast.office.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * office配置
 *
 * @author shoufeng
 */

@Configuration
@ConditionalOnProperty(prefix = "micro.fast.office", name = "enable", havingValue = "true")
@ComponentScan(value = {"com.micro.fast.office.configuration", "com.micro.fast.office.utils"})
@Slf4j
public class MicroFastOfficeConfiguration {

}
