package com.micro.fast.demo.doc.controller.docket2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Docket2的controller")
public class Docket2Controller {

    @ApiOperation(value = "kkk请求接口")
    @GetMapping("/kkkzzz")
    public void kkkzzz() {
    }
}
