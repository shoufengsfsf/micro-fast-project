# 日志模块使用说明

## 简介

日志模块主要实现了两个功能：1、通过mdc对用户的每次请求日志记录进行埋点隔离（对用户请求的接口名称、请求方法、以及用户唯一标示进行记录）。2、对使用了@SysLog(operation = "操作名称",enableDb = false)且enableDb=true的操作进行入库，micro-fast-log-starter模块仅提供入库操作接口，入库操作实现类由引入micro-fast-log-starter模块的应用提供（为了适应各类数据库以及日志表结构，日志模块日志入库的原则是谁引用谁实现）。

## 使用说明

具体使用案例请查看：[micro-fast-demo-log](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-demo/micro-fast-demo-log)

1、添加依赖

```java
<dependency>
    <groupId>com.shoufeng.project</groupId>
    <artifactId>micro-fast-log-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

2、修改application.yml配置

```java
# Tomcat
server:
  tomcat:
    uri-encoding: UTF-8
    max-threads: 1000
    min-spare-threads: 30
  port: 7070

  servlet:
    context-path: /
    session:
      cookie:
        http-only: false

spring:
  application:
    name: fast-demo-log
  profiles:
    active: dev
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB
      enabled: true
      location: ./data/file

micro:
  fast:
    log:
      #是否开启日志模块
      enable: true
      #请求用户唯一标示请求头名称
      token-header: accessToken
      #mdc拦截器级别
      interceptor-order: -10
```

3、实现SysLogService接口

```java
@Service
public class SysLogServiceImpl implements SysLogService {

    /**
     * 模拟获取登录信息
     *
     * @return
     */
    @Override
    public Result<SysUserInfoDto> getUserInfo() {
        Result<SysUserInfoDto> sysUserInfoDtoResult = new Result<>();
        sysUserInfoDtoResult.setCode(ResultCode.SUCCESS.getCode());
        SysUserInfoDto sysUserInfoDto = new SysUserInfoDto();
        sysUserInfoDto.setUsername("张三");
        sysUserInfoDtoResult.setData(sysUserInfoDto);
        return sysUserInfoDtoResult;
    }

    /**
     * 模拟入库操作
     *
     * @param sysLogDto
     * @return
     */
    @Override
    public Boolean saveSysLog(SysLogDto sysLogDto) {
        System.out.println("日志入库模拟操作；" + JSON.toJSONString(sysLogDto));
        return true;
    }

}
```

4、使用@SysLog注解记录操作

```java
/**
 * 模拟请求，所以我都用get了，方便一点
 *
 * @author shoufeng
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @SysLog(operation = "查询用户",enableDb = false)
    @GetMapping("/list")
    public List<String> list() {
        return userService.list();
    }

    @SysLog(operation = "新增用户",enableDb = true)
    @GetMapping("/save")
    public void save() {
        userService.save("赵六");
    }

    @SysLog(operation = "删除用户",enableDb = true)
    @GetMapping("/delete")
    public void delete() {
        userService.delete();
    }
}
```

5、模拟请求查看结果

查询用户

![查询用户](https://tva1.sinaimg.cn/large/007S8ZIlly1gf7dihdk8vj31zg0jmwlp.jpg)

新增用户

![新增用户](https://tva1.sinaimg.cn/large/007S8ZIlly1gf7disphvbj321m0mmdzc.jpg)

删除用户

![删除用户](https://tva1.sinaimg.cn/large/007S8ZIlly1gf7dj0gshpj322u0s614y.jpg)

