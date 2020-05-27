package com.micro.fast.log.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author shoufeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 是否写入数据库是
	 */
	boolean enableDb() default false;

	/**
	 * 方法操作名称
	 */
	String operation() default "";
}
