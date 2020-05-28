# 权限模块使用说明

## 简介

权限模块主要基于jwt实现，目前提供了客户端starter和服务端starter。

## 使用说明

具体使用案例请查看：[micro-fast-demo-auth](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-demo/micro-fast-demo-auth)

1、添加依赖

添加客户端依赖

```java
<dependency>
    <groupId>com.shoufeng.project</groupId>
    <artifactId>micro-fast-auth-jwt-client-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

添加服务端依赖

```java
<dependency>
    <groupId>com.shoufeng.project</groupId>
    <artifactId>micro-fast-auth-jwt-server-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

2、生成公钥密钥对

```java
public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    RsaKeyUtils rsaKeyUtils = new RsaKeyUtils();
    rsaKeyUtils.generatePairKey("abcdefg12345", "./public.key", "./private.key");
}
```

![生成密钥对](https://tva1.sinaimg.cn/large/007S8ZIlly1gf7zepz9qaj30g203yglr.jpg)

将公钥移入客户端

![将公钥移入客户端](https://tva1.sinaimg.cn/large/007S8ZIlly1gf7zf7gcsaj30j20a6my2.jpg)

将公钥、私钥移入服务端

![将公钥私钥移入服务端](https://tva1.sinaimg.cn/large/007S8ZIlly1gf7zfr7smej30iw09m74y.jpg)

3、修改application.yml配置

修改客户端application.yml配置

```java
micro:
  fast:
    auth:
      client:
        token-info:
          header-name: accessToken
          public-key: public.key
```

修改服务端application.yml配置

```java
micro:
  fast:
    auth:
      server:
        token-info:
          expire: 7200
          private-key: private.key
          public-key: public.key
```

4、开启客户端、服务端注解

客户端

```java
/**
 * @author shoufeng
 */
@SpringBootApplication
@EnableAuthClient
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

服务端

```java
/**
 * @author shoufeng
 */
@SpringBootApplication
@EnableAuthServer
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

5、编写Controller

客户端Controller

```java
/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/client/auth")
public class AuthController {

    @Resource
    private JwtTokenClientUtils jwtTokenClientUtils;

    @Resource
    private MicroFastAuthClientProperties microFastAuthClientProperties;

    @GetMapping("/tokenInfo")
    public String getTokenInfo(HttpServletRequest requests) {
        String token = requests.getHeader(microFastAuthClientProperties.getTokenInfo().getHeaderName());
        return JSON.toJSONString(jwtTokenClientUtils.getUserInfo(token));
    }
}
```

服务端Controller

```java
/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/server/auth")
public class AuthController {

    @Resource
    private JwtTokenServerUtils jwtTokenServerUtils;

    @Resource
    private MicroFastAuthServerProperties microFastAuthServerProperties;

    @GetMapping("/token")
    public String getToken(HttpServletRequest requests) {
        JwtContentInfo jwtContentInfo = new JwtContentInfo();
        jwtContentInfo.setUserId(1L);
        jwtContentInfo.setUserName("张三");
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("年龄", "18");
        jwtContentInfo.setUserInfo(userInfoMap);
        Token token = jwtTokenServerUtils.generateUserToken(jwtContentInfo, microFastAuthServerProperties.getTokenInfo().getExpire());
        return JSON.toJSONString(token);
    }

}
```

6、请求测试

向服务端获取token

![向服务端获取token](https://tva1.sinaimg.cn/large/007S8ZIlly1gf8068t7kmj31c80u0jy3.jpg)

客户端解析服务端生成的token

![客户端解析服务端发出的token](https://tva1.sinaimg.cn/large/007S8ZIlly1gf806ysoacj31eg0u0q8b.jpg)

