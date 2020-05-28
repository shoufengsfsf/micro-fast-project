package com.micro.fast.auth.server.controller;

import com.alibaba.fastjson.JSON;
import com.micro.fast.auth.jwt.common.pojo.JwtContentInfo;
import com.micro.fast.auth.jwt.common.pojo.Token;
import com.micro.fast.auth.jwt.common.utils.RsaKeyUtils;
import com.micro.fast.auth.jwt.server.configuration.properties.MicroFastAuthServerProperties;
import com.micro.fast.auth.jwt.server.utils.JwtTokenServerUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/server/auth")
public class AuthController {

    @Resource
    private JwtTokenServerUtils jwtTokenServerUtils;

    @Resource
    private MicroFastAuthServerProperties microFastAuthServerProperties;

    @GetMapping("/token")
    public String getToken(HttpServletRequest requests) {
        JwtContentInfo jwtContentInfo = new JwtContentInfo();
        jwtContentInfo.setUserId(1L);
        jwtContentInfo.setUserName("张三");
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("年龄", "18");
        jwtContentInfo.setUserInfo(userInfoMap);
        Token token = jwtTokenServerUtils.generateUserToken(jwtContentInfo, microFastAuthServerProperties.getTokenInfo().getExpire());
        return JSON.toJSONString(token);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        RsaKeyUtils rsaKeyUtils = new RsaKeyUtils();
        rsaKeyUtils.generatePairKey("abcdefg12345", "./public.key", "./private.key");
    }
}
