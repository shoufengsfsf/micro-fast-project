package com.micro.fast.minio.request;

import com.micro.fast.minio.constant.MicroFastMinioDownloadMethodTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 下载url请求参数
 *
 * @author shoufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MicroFastMinioDownloadRequest {

    /**
     * 存储桶名称
     */
    @NotNull(message = "存储桶名称")
    private String bucketName;

    /**
     * 存储桶里的对象名称
     */
    @NotNull(message = "存储桶里的对象名称")
    private String objectName;

    /**
     * 失效时间（以秒为单位），不得大于七天。
     */
    private Integer expires;

    /**
     * 下载方式类型
     */
    @NotNull(message = "下载方式类型")
    private MicroFastMinioDownloadMethodTypeEnum downloadMethodTypeEnum;
}
