package com.micro.fast.auth.jwt.server;

import com.micro.fast.auth.jwt.server.configuration.MicroFastAuthServerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 认证服务 的服务端配置
 *
 * @author zhihao.mao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MicroFastAuthServerConfiguration.class)
@Documented
@Inherited
public @interface EnableAuthServer {
}
