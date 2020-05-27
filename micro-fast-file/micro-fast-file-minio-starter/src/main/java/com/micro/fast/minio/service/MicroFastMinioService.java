package com.micro.fast.minio.service;

import com.micro.fast.minio.request.MicroFastMinioDeleteRequest;
import com.micro.fast.minio.request.MicroFastMinioDownloadRequest;
import com.micro.fast.minio.request.MicroFastMinioListRequest;
import com.micro.fast.minio.request.MicroFastMinioUploadRequest;
import com.micro.fast.minio.response.MicroFastMinioListResponse;
import io.minio.errors.*;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author shoufeng
 */
public interface MicroFastMinioService {

    /**
     * 上传
     */
    String upload(MicroFastMinioUploadRequest fastMinioUploadRequest) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException, IllegalAccessException, InvalidArgumentException, InvalidExpiresRangeException;

    /**
     * 下载
     */
    String download(MicroFastMinioDownloadRequest fastMinioDownloadRequest, HttpServletResponse httpServletResponse) throws IllegalAccessException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidExpiresRangeException;

    /**
     * 删除
     */
    void delete(MicroFastMinioDeleteRequest fastMinioDeleteRequest) throws IllegalAccessException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException;

    /**
     * 查询
     */
    MicroFastMinioListResponse list(MicroFastMinioListRequest fastMinioListRequest) throws IllegalAccessException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException;

}
