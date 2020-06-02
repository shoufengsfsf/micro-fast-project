package com.micro.fast.demo.discovery.nacos.controller;

import com.micro.fast.demo.discovery.nacos.feign.DemoFeignClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private DemoFeignClient demoFeignClient;

    @GetMapping("/test11/{name}")
    public String test1(@PathVariable(name = "name") String name) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("fast-demo-discovery-nacos-producer");
        String targetUrl = serviceInstance.getUri() + "/demo/list/" + name;
        return restTemplate.getForEntity(targetUrl, String.class).getBody();
    }

    @GetMapping("/test22/{name}")
    public String test2(@PathVariable(name = "name") String name) throws InterruptedException {
        return demoFeignClient.save(name);
    }

    @GetMapping("/test33/{name}")
    public String test3(@PathVariable(name = "name") String name) throws Exception {
        return demoFeignClient.delete(name);
    }

}
