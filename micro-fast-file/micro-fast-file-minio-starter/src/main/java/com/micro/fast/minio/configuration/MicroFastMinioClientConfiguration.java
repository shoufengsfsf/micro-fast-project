package com.micro.fast.minio.configuration;

import com.micro.fast.minio.configuration.properties.MicroFastMinioClientProperties;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * minio客户端配置
 *
 * @author shoufeng
 */
@Configuration
@ConditionalOnProperty(prefix = "micro.fast.minio", value = "enable", havingValue = "true")
@ComponentScan(value = {"com.micro.fast.minio.configuration", "com.micro.fast.minio.service"})
public class MicroFastMinioClientConfiguration {

    @Resource
    private MicroFastMinioClientProperties fastMinioClientProperties;

    @Bean
    @ConditionalOnMissingBean(value = MinioClient.class)
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(fastMinioClientProperties.getEndpoint(), fastMinioClientProperties.getAccessKey(), fastMinioClientProperties.getSecretKey());
    }
}
