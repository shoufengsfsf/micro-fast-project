package com.micro.fast.auth.jwt.client.configuration;


import com.micro.fast.auth.jwt.client.configuration.properties.MicroFastAuthClientProperties;
import com.micro.fast.auth.jwt.client.utils.JwtTokenClientUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 客户端认证配置
 *
 * @author zhihao.mao
 */
@EnableConfigurationProperties(value = {
        MicroFastAuthClientProperties.class
})
public class MicroFastAuthClientConfiguration {

    @Bean
    public JwtTokenClientUtils getJwtTokenClientUtils(MicroFastAuthClientProperties authClientProperties) {
        return new JwtTokenClientUtils(authClientProperties);
    }

}
