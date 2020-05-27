package com.micro.fast.minio.request;

import com.micro.fast.minio.constant.MicroFastMinioUploadMethodTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Map;

/**
 * 上传请求参数
 *
 * @author shoufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MicroFastMinioUploadRequest {

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
     * 用于本地上传文件名（比如: /mnt/photos/island.jpg）
     */
    private String fileName;

    /**
     * 要上传的流（和fileName二选一即可）
     */
    private InputStream stream;

    /**
     * 要上传的stream的size
     */
    private Long size;

    /**
     * 对象的自定义/附加元数据（比如: Content-Type:application/octet-stream）
     */
    private Map<String, String> headerMap;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 失效时间（以秒为单位），不得大于七天。
     */
    private Integer expires;

    /**
     * 上传方式类型
     */
    @NotNull(message = "上传方式类型")
    private MicroFastMinioUploadMethodTypeEnum uploadMethodTypeEnum;
}
