package com.micro.fast.office.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务异常代码
 *
 * @author shoufeng
 */
@AllArgsConstructor
public enum MicroFastOfficeExceptionCodeEnum {

    /**
     * 失败
     */
    FAIL(8001, ""),
    EXCEL_FAIL(8002, "EXCEL操作异常"),
    PDF_FAIL(8003, "PDF操作异常"),
    WORD_FAIL(8004, "WORD操作异常"),
    ;

    @Getter
    private int code;
    @Getter
    private String message;
}
