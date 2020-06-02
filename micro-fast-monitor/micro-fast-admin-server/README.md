# 监控模块使用说明

## 简介

监控模块主要基于SpringBootAdmin实现，主要目的是对应用的健康、性能、以及日志进行监控。

## 使用说明

具体使用案例请查看：[micro-fast-admin-server](https://github.com/shoufengsfsf/micro-fast-project/tree/master/micro-fast-monitor)、[micro-fast-demo-log](https://github.com/shoufengsfsf/micro-fast-project/tree/master/micro-fast-demo/micro-fast-demo-log)

1、配置micro-fast-admin-server

```java
spring:
  application:
    name: fast-admin-server
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
      discovery:
        server-addr: 127.0.0.1:8848
        metadata:
          user:
            name: ${spring.security.user.name}
            password: ${spring.security.user.password}
  #登录用户密码也可以配置在nocas中配置
  security:
    user:
      name: admin
      password: admin
server:
  port: 8765
```

2、启动应用

![image-20200602220437102](https://tva1.sinaimg.cn/large/007S8ZIlly1gfeao4u8unj31a10u076f.jpg)

3、登录并查看监控的应用

![image-20200602221203294](https://tva1.sinaimg.cn/large/007S8ZIlly1gfeavt7xpxj31px0u0dk6.jpg)

4、向micro-fast-admin-server注册新应用

配置新应用

```java
spring:
  boot:
    admin:
      client:
        url: http://127.0.0.1:8765/
        username: admin
        password: admin
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS
    logfile:
      external-file: ./logs/${spring.application.name}/log_all.log
```

启动要被注册的应用并查看是否监控成功

![image-20200602221337189](https://tva1.sinaimg.cn/large/007S8ZIlly1gfeaxfs4caj31o80u0grg.jpg)

查看应用日志

![image-20200602221405373](https://tva1.sinaimg.cn/large/007S8ZIlly1gfeaxy2yzaj31oi0u018i.jpg)