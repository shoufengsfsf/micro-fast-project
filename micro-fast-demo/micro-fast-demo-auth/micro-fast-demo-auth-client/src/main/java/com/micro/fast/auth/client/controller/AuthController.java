package com.micro.fast.auth.client.controller;

import com.alibaba.fastjson.JSON;
import com.micro.fast.auth.jwt.client.configuration.properties.MicroFastAuthClientProperties;
import com.micro.fast.auth.jwt.client.utils.JwtTokenClientUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/client/auth")
public class AuthController {

    @Resource
    private JwtTokenClientUtils jwtTokenClientUtils;

    @Resource
    private MicroFastAuthClientProperties microFastAuthClientProperties;

    @GetMapping("/tokenInfo")
    public String getTokenInfo(HttpServletRequest requests) {
        String token = requests.getHeader(microFastAuthClientProperties.getTokenInfo().getHeaderName());
        return JSON.toJSONString(jwtTokenClientUtils.getUserInfo(token));
    }
}
