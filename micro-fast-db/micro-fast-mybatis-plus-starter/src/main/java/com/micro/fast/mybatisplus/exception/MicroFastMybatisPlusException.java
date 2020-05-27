package com.micro.fast.mybatisplus.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 服务异常
 *
 * @author shoufeng
 */
public class MicroFastMybatisPlusException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 异常代码
     */
    @Getter
    @Setter
    private int code;

    /**
     * 异常消息
     */
    @Getter
    @Setter
    private String message;

    public MicroFastMybatisPlusException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public MicroFastMybatisPlusException(int code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }
}
