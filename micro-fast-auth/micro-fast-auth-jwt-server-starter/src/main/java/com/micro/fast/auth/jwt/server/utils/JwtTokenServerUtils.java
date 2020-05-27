package com.micro.fast.auth.jwt.server.utils;

import com.micro.fast.auth.jwt.common.exception.MicroFastAuthJwtException;
import com.micro.fast.auth.jwt.common.pojo.JwtContentInfo;
import com.micro.fast.auth.jwt.common.pojo.Token;
import com.micro.fast.auth.jwt.common.utils.JwtUtils;
import com.micro.fast.auth.jwt.server.configuration.properties.MicroFastAuthServerProperties;
import lombok.AllArgsConstructor;

/**
 * jwt token 工具
 *
 * @author zhihao.mao
 */
@AllArgsConstructor
public class JwtTokenServerUtils {
    /**
     * 认证服务端使用，如 authority-server
     * 生成和 解析token
     */
    private MicroFastAuthServerProperties authServerProperties;

    /**
     * 生成token
     *
     * @param jwtInfo
     * @param expire
     * @return
     * @throws MicroFastAuthJwtException
     */
    public Token generateUserToken(JwtContentInfo jwtInfo, Integer expire) throws MicroFastAuthJwtException {

        MicroFastAuthServerProperties.TokenInfo tokenInfo = authServerProperties.getTokenInfo();

        if (expire == null || expire <= 0) {
            expire = tokenInfo.getExpire();
        }
        return JwtUtils.generateUserToken(jwtInfo, tokenInfo.getPrivateKey(), expire);
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     * @throws MicroFastAuthJwtException
     */
    public JwtContentInfo getUserInfo(String token) throws MicroFastAuthJwtException {

        MicroFastAuthServerProperties.TokenInfo userTokenInfo = authServerProperties.getTokenInfo();

        return JwtUtils.getJwtFromToken(token, userTokenInfo.getPublicKey());
    }


}
