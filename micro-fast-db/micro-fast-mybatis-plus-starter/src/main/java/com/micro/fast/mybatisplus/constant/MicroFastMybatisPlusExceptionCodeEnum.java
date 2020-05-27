package com.micro.fast.mybatisplus.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务异常代码
 *
 * @author shoufeng
 */
@AllArgsConstructor
public enum MicroFastMybatisPlusExceptionCodeEnum {

    /**
     * 失败
     */
    FAIL(4001, ""),
    ;

    @Getter
    private int code;
    @Getter
    private String message;
}
