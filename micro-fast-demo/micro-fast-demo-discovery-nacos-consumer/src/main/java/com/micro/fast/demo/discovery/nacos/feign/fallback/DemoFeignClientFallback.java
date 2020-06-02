package com.micro.fast.demo.discovery.nacos.feign.fallback;

import com.micro.fast.demo.discovery.nacos.feign.DemoFeignClient;
import org.springframework.stereotype.Component;

@Component
public class DemoFeignClientFallback implements DemoFeignClient {

    @Override
    public String list(String name) {
        return "list服务降级";
    }

    @Override
    public String save(String name) throws InterruptedException {
        return "save服务降级";
    }

    @Override
    public String delete(String name) throws Exception {
        return "delete服务降级";
    }
}
