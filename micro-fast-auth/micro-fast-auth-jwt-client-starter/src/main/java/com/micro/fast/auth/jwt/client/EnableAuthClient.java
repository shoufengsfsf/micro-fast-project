package com.micro.fast.auth.jwt.client;

import com.micro.fast.auth.jwt.client.configuration.MicroFastAuthClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用授权client
 *
 * @author zhihao.mao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MicroFastAuthClientConfiguration.class})
@Documented
@Inherited
public @interface EnableAuthClient {
}
