package com.micro.fast.demo.discovery.nacos.feign;

import com.micro.fast.demo.discovery.nacos.feign.fallback.DemoFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "fast-demo-discovery-nacos-producer", fallback = DemoFeignClientFallback.class)
public interface DemoFeignClient {

    @GetMapping("/demo/list/{name}")
    public String list(@PathVariable(name = "name") String name);

    @GetMapping("/demo/save/{name}")
    public String save(@PathVariable(name = "name") String name) throws InterruptedException;

    @GetMapping("/demo/delete/{name}")
    public String delete(@PathVariable(name = "name") String name) throws Exception;
}
