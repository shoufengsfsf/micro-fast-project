package com.micro.fast.demo.discovery.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/list/{name}")
    public String list(@PathVariable(name = "name") String name) {
        return "响应list: " + name;
    }

    @GetMapping("/save/{name}")
    public String save(@PathVariable(name = "name") String name) throws InterruptedException {
        Thread.sleep(1000 * 60L);
        return "响应save: " + name;
    }

    @GetMapping("/delete/{name}")
    public String delete(@PathVariable(name = "name") String name) throws Exception {
        throw new Exception("出错了");
    }
}
