package com.micro.fast.minio.request;

import com.micro.fast.minio.constant.MicroFastMinioListMethodTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 查询请求参数
 *
 * @author shoufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MicroFastMinioListRequest {

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 对象名称的前缀
     */
    private String prefix;

    /**
     * 是否递归查找，如果是false,就模拟文件夹结构查找。
     */
    private boolean recursive = true;

    /**
     * 查询方式类型
     */
    @NotNull(message = "查询方式类型")
    private MicroFastMinioListMethodTypeEnum listMethodTypeEnum;
}
