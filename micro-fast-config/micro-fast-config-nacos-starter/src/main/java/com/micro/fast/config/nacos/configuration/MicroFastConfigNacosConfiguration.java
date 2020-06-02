package com.micro.fast.config.nacos.configuration;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.micro.fast.config.nacos.runner.MicroFastConfigNacosLogCommandLineRunnerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author shoufeng
 */
@Configuration
@ConditionalOnProperty(prefix = "micro.fast.config.nacos", value = "enable", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = {"com.micro.fast.config.nacos.configuration"})
public class MicroFastConfigNacosConfiguration {

    @Resource
    private NacosConfigProperties nacosDiscoveryProperties;

    @Bean
    public MicroFastConfigNacosLogCommandLineRunnerImpl microFastConfigNacosLogCommandLineRunner() {
        return new MicroFastConfigNacosLogCommandLineRunnerImpl(nacosDiscoveryProperties);
    }
}
