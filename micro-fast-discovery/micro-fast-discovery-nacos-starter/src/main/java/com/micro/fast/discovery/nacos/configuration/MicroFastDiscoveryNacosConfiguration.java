package com.micro.fast.discovery.nacos.configuration;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.micro.fast.discovery.nacos.runner.MicroFastDiscoveryNacosLogCommandLineRunnerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author shoufeng
 */
@Configuration
@ConditionalOnProperty(prefix = "micro.fast.discovery.nacos", value = "enable", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = {"com.micro.fast.discovery.nacos.configuration"})
public class MicroFastDiscoveryNacosConfiguration {

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Bean
    public MicroFastDiscoveryNacosLogCommandLineRunnerImpl microFastDiscoveryNacosLogCommandLineRunner() {
        return new MicroFastDiscoveryNacosLogCommandLineRunnerImpl(nacosDiscoveryProperties);
    }

}
