package com.micro.fast.minio.service.impl;

import com.google.common.collect.Lists;
import com.micro.fast.minio.constant.MicroFastMinioConstant;
import com.micro.fast.minio.constant.MicroFastMinioExceptionCodeEnum;
import com.micro.fast.minio.exception.MicroFastMinioException;
import com.micro.fast.minio.request.MicroFastMinioDeleteRequest;
import com.micro.fast.minio.request.MicroFastMinioDownloadRequest;
import com.micro.fast.minio.request.MicroFastMinioListRequest;
import com.micro.fast.minio.request.MicroFastMinioUploadRequest;
import com.micro.fast.minio.response.MicroFastMinioListResponse;
import com.micro.fast.minio.service.MicroFastMinioService;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shoufeng
 */
@Service
@Slf4j
public class MicroFastMinioServiceImpl implements MicroFastMinioService {

    @Resource
    private MinioClient minioClient;

    @Override
    public String upload(MicroFastMinioUploadRequest fastMinioUploadRequest) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException, IllegalAccessException, InvalidArgumentException, InvalidExpiresRangeException {
        String bucketName = fastMinioUploadRequest.getBucketName();
        if (!minioClient.bucketExists(bucketName)) {
            minioClient.makeBucket(bucketName);
            log.info("bucketName不存在: 创建{}成功", bucketName);
        }
        switch (fastMinioUploadRequest.getUploadMethodTypeEnum()) {
            case LOCAL: {
                minioClient.putObject(bucketName, fastMinioUploadRequest.getObjectName(), fastMinioUploadRequest.getFileName());
                break;
            }
            case STREAM: {
                if (ObjectUtils.isNotEmpty(fastMinioUploadRequest.getHeaderMap())) {
                    minioClient.putObject(bucketName, fastMinioUploadRequest.getObjectName(), fastMinioUploadRequest.getStream(), fastMinioUploadRequest.getSize(), fastMinioUploadRequest.getHeaderMap());
                    break;
                }
                minioClient.putObject(bucketName, fastMinioUploadRequest.getObjectName(), fastMinioUploadRequest.getStream(), fastMinioUploadRequest.getSize(), fastMinioUploadRequest.getContentType());
                break;
            }
            default: {
                log.info("无效上传类型: {}", fastMinioUploadRequest.getUploadMethodTypeEnum());
                return StringUtils.EMPTY;
            }
        }

        if (ObjectUtils.isNotEmpty(fastMinioUploadRequest.getExpires()) && fastMinioUploadRequest.getExpires() < MicroFastMinioConstant.DEFAULT_EXPIRY_TIME) {
            return minioClient.presignedGetObject(fastMinioUploadRequest.getBucketName(), fastMinioUploadRequest.getObjectName(), fastMinioUploadRequest.getExpires());
        }
        //采用默认过期时间7天
        return minioClient.presignedGetObject(fastMinioUploadRequest.getBucketName(), fastMinioUploadRequest.getObjectName());
    }

    @Override
    public String download(MicroFastMinioDownloadRequest fastMinioDownloadRequest, HttpServletResponse httpServletResponse) throws IllegalAccessException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidExpiresRangeException {
        switch (fastMinioDownloadRequest.getDownloadMethodTypeEnum()) {
            case DIRECT: {
                InputStream inputStream = minioClient.getObject(fastMinioDownloadRequest.getBucketName(), fastMinioDownloadRequest.getObjectName());
                httpServletResponse.reset();
                httpServletResponse.setContentType("application/octet-stream; charset=utf-8");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fastMinioDownloadRequest.getObjectName(), "utf-8"));
                ServletOutputStream outputStream = httpServletResponse.getOutputStream();
                FileCopyUtils.copy(inputStream, outputStream);
                return StringUtils.EMPTY;
            }
            case URL: {
                Integer expires = fastMinioDownloadRequest.getExpires();
                if (ObjectUtils.isEmpty(expires) || MicroFastMinioConstant.DEFAULT_EXPIRY_TIME < expires) {
                    return minioClient.presignedGetObject(fastMinioDownloadRequest.getBucketName(), fastMinioDownloadRequest.getObjectName());
                }
                return minioClient.presignedGetObject(fastMinioDownloadRequest.getBucketName(), fastMinioDownloadRequest.getObjectName(), expires);
            }
            default: {
                log.info("无效下载类型: {}", fastMinioDownloadRequest.getDownloadMethodTypeEnum());
                return StringUtils.EMPTY;
            }
        }
    }

    @Override
    public void delete(MicroFastMinioDeleteRequest fastMinioDeleteRequest) throws IllegalAccessException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        switch (fastMinioDeleteRequest.getDeleteMethodTypeEnum()) {
            case Bucket: {
                minioClient.removeBucket(fastMinioDeleteRequest.getBucketName());
                break;
            }
            case Object: {
                minioClient.removeObject(fastMinioDeleteRequest.getBucketName(), fastMinioDeleteRequest.getObjectName());
                break;
            }
            default: {
                log.info("无效删除类型: {}", fastMinioDeleteRequest.getDeleteMethodTypeEnum());
            }
        }
    }

    @Override
    public MicroFastMinioListResponse list(MicroFastMinioListRequest fastMinioListRequest) throws IllegalAccessException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        switch (fastMinioListRequest.getListMethodTypeEnum()) {
            case Bucket: {
                List<Bucket> bucketList = minioClient.listBuckets();
                List<MicroFastMinioListResponse.BucketObjectListData> bucketObjectListDataList = bucketList
                        .stream()
                        .map(bucket -> MicroFastMinioListResponse.BucketObjectListData.builder().bucket(bucket).build())
                        .collect(Collectors.toList());
                return MicroFastMinioListResponse.builder().bucketObjectListDataList(bucketObjectListDataList).build();
            }
            case Object: {
                if (StringUtils.isBlank(fastMinioListRequest.getBucketName())) {
                    throw new MicroFastMinioException(MicroFastMinioExceptionCodeEnum.FAIL.getCode(), "查询对象列表失败: 存储桶名称不能为空");
                }
                Optional<Bucket> optionalBucket = minioClient.listBuckets()
                        .stream()
                        .filter(bucket -> bucket.name().equalsIgnoreCase(fastMinioListRequest.getBucketName()))
                        .findFirst();
                if (optionalBucket.isPresent()) {
                    Iterable<Result<Item>> resultIterable = minioClient.listObjects(fastMinioListRequest.getBucketName(), fastMinioListRequest.getPrefix(), fastMinioListRequest.isRecursive());
                    List<Item> objectList = new ArrayList<>();
                    for (Result<Item> itemResult : resultIterable) {
                        objectList.add(itemResult.get());
                    }
                    List<MicroFastMinioListResponse.BucketObjectListData> dataList = Lists.newArrayList(MicroFastMinioListResponse.BucketObjectListData.builder().bucket(optionalBucket.get()).objectList(objectList).build());
                    return MicroFastMinioListResponse.builder().bucketObjectListDataList(dataList).build();
                }
            }
            default: {
                log.info("无效查询类型: {}", fastMinioListRequest.getListMethodTypeEnum());
            }
        }
        return null;
    }

}
