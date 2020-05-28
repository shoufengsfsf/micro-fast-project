# 文档模块使用说明

## 简介

文档模块主要基于swagger实现，对swagger的常用配置做了二次封装。

## 使用说明

具体使用案例请查看：[micro-fast-demo-doc](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-demo/micro-fast-demo-doc)

1、添加依赖

```java
<dependency>
    <groupId>com.shoufeng.project</groupId>
    <artifactId>micro-fast-swagger-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

2、编写Controller

```java
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
```

```java
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
```

3、修改application.yml配置

```java
micro:
  fast:
    swagger:
      #是否开启
      enable: true
      micro-fast-swagger-docket-list:
        - enable: true
          #host信息
          host: 127.0.0.1
          #分组名称
          groupName: groupName第2个
          #wagger会解析的包路径
          basePackage: com.micro.fast.demo.doc.controller.docket2
          micro-fast-swagger-api-info:
            #版本
            version: 版本02
            #标题
            title: 标题02
            #描述
            description: 描述02
            #服务条款
            termsOfServiceUrl: 服务条款02
            #证书
            license: 证书02
            #证书地址
            licenseUrl:  证书地址02
            #联系人
            contact:
              name:  守风
              url:   baidu.com
              email: 111111@163.com
          #是否开启
        - enable: true
          #host信息
          host: 127.0.0.1
          #分组名称
          groupName: groupName第一个
          #wagger会解析的包路径
          basePackage: com.micro.fast.demo.doc.controller.docket1
          micro-fast-swagger-api-info:
            #版本
            version: 版本01
            #标题
            title: 标题01
            #描述
            description: 描述01
            #服务条款
            termsOfServiceUrl: 服务条款01
            #证书
            license: 证书01
            #证书地址
            licenseUrl:  证书地址01
            #联系人
            contact:
              name:  守风
              url:   baidu.com
              email: 111111@163.com
```

4、查看swagger http://127.0.0.1:7070/swagger-ui.html

![swaggger](https://tva1.sinaimg.cn/large/007S8ZIlly1gf88b6uet8j327o0t67ao.jpg)

