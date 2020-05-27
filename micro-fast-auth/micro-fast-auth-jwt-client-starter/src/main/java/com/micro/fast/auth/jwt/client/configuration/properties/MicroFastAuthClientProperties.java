package com.micro.fast.auth.jwt.client.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 客户端认证配置
 *
 * @author zhihao.mao
 */
@ConfigurationProperties(prefix = MicroFastAuthClientProperties.PREFIX)
@Data
@NoArgsConstructor
public class MicroFastAuthClientProperties {

    public static final String PREFIX = "micro.fast.auth.client";

    private TokenInfo tokenInfo;

    @Data
    public static class TokenInfo {
        /**
         * 请求头名称
         */
        private String headerName = "accessToken";
        /**
         * 公钥解密
         */
        private String publicKey = "public.key";
    }

}
