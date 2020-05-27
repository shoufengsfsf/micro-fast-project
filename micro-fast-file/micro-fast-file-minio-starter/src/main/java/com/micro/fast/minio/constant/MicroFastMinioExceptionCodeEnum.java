package com.micro.fast.minio.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务异常代码
 *
 * @author shoufeng
 */
@AllArgsConstructor
public enum MicroFastMinioExceptionCodeEnum {

    /**
     * 失败
     */
    FAIL(5001, ""),
    ;

    @Getter
    private int code;
    @Getter
    private String message;
}
