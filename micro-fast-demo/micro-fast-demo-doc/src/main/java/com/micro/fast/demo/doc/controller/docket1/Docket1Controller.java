package com.micro.fast.demo.doc.controller.docket1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Docket1的controller")
public class Docket1Controller {

    @ApiOperation(value = "test请求接口")
    @GetMapping("/test")
    public void test() {
    }
}
