package com.micro.fast.demo.config.nacos.controller;

import com.alibaba.fastjson.JSON;
import com.micro.fast.demo.config.nacos.properties.UserProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/demo")
@RefreshScope
public class DemoController {

    @Resource
    private UserProperties userProperties;

    @GetMapping("/test")
    public String test() {
        return JSON.toJSONString(userProperties);
    }
}
