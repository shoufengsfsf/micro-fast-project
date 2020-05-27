package com.micro.fast.auth.jwt.server.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 认证服务端 属性
 *
 * @author zhihao.mao
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = MicroFastAuthServerProperties.PREFIX)
public class MicroFastAuthServerProperties {

    public static final String PREFIX = "authentication";

    private TokenInfo tokenInfo;

    @Data
    public static class TokenInfo {
        /**
         * 过期时间
         */
        private Integer expire = 7200;
        /**
         * 加密 服务使用
         */
        private String privateKey;
        /**
         * 解密
         */
        private String publicKey;
    }

}
