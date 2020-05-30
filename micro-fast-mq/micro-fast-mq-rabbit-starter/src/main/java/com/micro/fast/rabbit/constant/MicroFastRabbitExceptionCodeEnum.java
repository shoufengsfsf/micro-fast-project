package com.micro.fast.rabbit.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务异常代码
 *
 * @author shoufeng
 */
@AllArgsConstructor
public enum MicroFastRabbitExceptionCodeEnum {

    /**
     * 失败
     */
    FAIL(7001, ""),
    ;

    @Getter
    private int code;
    @Getter
    private String message;
}
