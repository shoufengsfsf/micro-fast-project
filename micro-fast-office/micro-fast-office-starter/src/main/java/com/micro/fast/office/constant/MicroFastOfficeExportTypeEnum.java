package com.micro.fast.office.constant;

import lombok.AllArgsConstructor;

/**
 * 导出方式
 *
 * @author shoufeng
 */
@AllArgsConstructor
public enum MicroFastOfficeExportTypeEnum {

    /**
     * 直接导出
     */
    DIRECT_EXPORT,

    /**
     * 生成下载链接导出，依赖minio
     */
    URL_EXPORT,
    ;
}
