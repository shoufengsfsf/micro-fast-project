package com.micro.fast.demo.doc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Demo1的controller")
public class Demo1Controller {

    @ApiOperation(value = "lll请求接口")
    @GetMapping("/lll")
    public void lll() {
    }
}
