package com.micro.fast.utils.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author shoufeng
 */

@AllArgsConstructor
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "success"),
    /**
     * 失败
     */
    FAIL(500, "fail");

    @Getter
    private int code;
    
    @Getter
    private String massage;
}
