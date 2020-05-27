package com.micro.fast.minio.request;

import com.micro.fast.minio.constant.MicroFastMinioDeleteMethodTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 删除请求参数
 *
 * @author shoufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MicroFastMinioDeleteRequest {

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
     * 删除方式类型
     */
    @NotNull(message = "删除方式类型")
    private MicroFastMinioDeleteMethodTypeEnum deleteMethodTypeEnum;
}
