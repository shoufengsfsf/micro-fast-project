package com.micro.fast.auth.jwt.server.configuration;


import com.micro.fast.auth.jwt.server.configuration.properties.MicroFastAuthServerProperties;
import com.micro.fast.auth.jwt.server.utils.JwtTokenServerUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 认证服务端配置
 *
 * @author zhihao.mao
 */
@EnableConfigurationProperties(value = {
        MicroFastAuthServerProperties.class,
})
public class MicroFastAuthServerConfiguration {

    @Bean
    public JwtTokenServerUtils getJwtTokenServerUtils(MicroFastAuthServerProperties authServerProperties) {
        return new JwtTokenServerUtils(authServerProperties);
    }

}
