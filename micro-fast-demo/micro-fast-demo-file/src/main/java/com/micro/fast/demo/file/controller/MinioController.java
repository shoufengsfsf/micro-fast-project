package com.micro.fast.demo.file.controller;

import com.alibaba.fastjson.JSON;
import com.micro.fast.minio.constant.MicroFastMinioDeleteMethodTypeEnum;
import com.micro.fast.minio.constant.MicroFastMinioDownloadMethodTypeEnum;
import com.micro.fast.minio.constant.MicroFastMinioListMethodTypeEnum;
import com.micro.fast.minio.constant.MicroFastMinioUploadMethodTypeEnum;
import com.micro.fast.minio.request.MicroFastMinioDeleteRequest;
import com.micro.fast.minio.request.MicroFastMinioDownloadRequest;
import com.micro.fast.minio.request.MicroFastMinioListRequest;
import com.micro.fast.minio.request.MicroFastMinioUploadRequest;
import com.micro.fast.minio.service.MicroFastMinioService;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("minio")
public class MinioController {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MicroFastMinioService fastMinioService;

    @GetMapping("bucketList")
    public String bucketList() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        return JSON.toJSONString(minioClient.listBuckets());
    }

    @GetMapping("deleteBucket")
    public void deleteBucket() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        minioClient.removeBucket("abucket");
    }

    @GetMapping("listFileList")
    public void listFileList() throws XmlPullParserException, InsufficientDataException, NoSuchAlgorithmException, IOException, NoResponseException, InvalidKeyException, InternalException, InvalidBucketNameException, ErrorResponseException {
        for (Result<Item> abucket : minioClient.listObjects("abucket")) {
            Item item = abucket.get();
            System.out.println(item.name);
            System.out.println(item.namespaceDictionary);
            System.out.println(JSON.toJSONString(item));
        }
    }

    @GetMapping("object")
    public void getObject(HttpServletResponse httpServletResponse) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        InputStream inputStream = minioClient.getObject("abucket", "jnaj_environment.sql");
        httpServletResponse.reset();
        httpServletResponse.setContentType("application/octet-stream; charset=utf-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("jnaj_environment.sql", "utf-8"));
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        FileCopyUtils.copy(inputStream, servletOutputStream);
    }

    /**
     * 本地文件上传
     *
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     * @throws NoSuchAlgorithmException
     * @throws RegionConflictException
     * @throws InvalidKeyException
     * @throws InvalidExpiresRangeException
     * @throws IllegalAccessException
     * @throws InvalidArgumentException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws InternalException
     */
    @GetMapping("localfile")
    public String test() throws IOException, XmlPullParserException, NoSuchAlgorithmException, RegionConflictException, InvalidKeyException, InvalidExpiresRangeException, IllegalAccessException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        MicroFastMinioUploadRequest fastMinioUploadRequest = MicroFastMinioUploadRequest.builder().bucketName("abucket").objectName("ERP部署文档.doc").fileName("/Users/shoufeng/Desktop/心怡仓/ERP部署文档.doc").expires(60 * 2).uploadMethodTypeEnum(MicroFastMinioUploadMethodTypeEnum.LOCAL).build();
        return fastMinioService.upload(fastMinioUploadRequest);
    }

    /**
     * 文件下载链接获取
     *
     * @param httpServletResponse
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidExpiresRangeException
     * @throws IllegalAccessException
     * @throws InvalidArgumentException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws InternalException
     */
    @GetMapping("download")
    public String download(HttpServletResponse httpServletResponse) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidExpiresRangeException, IllegalAccessException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        MicroFastMinioDownloadRequest fastMinioDownloadRequest = MicroFastMinioDownloadRequest.builder().bucketName("abucket").objectName("ERP部署文档.doc").downloadMethodTypeEnum(MicroFastMinioDownloadMethodTypeEnum.URL).build();
        return fastMinioService.download(fastMinioDownloadRequest, httpServletResponse);
    }

    /**
     * 文件删除
     *
     * @throws IOException
     * @throws XmlPullParserException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalAccessException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws InternalException
     */
    @GetMapping("/delete")
    public void delete() throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IllegalAccessException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        MicroFastMinioDeleteRequest fastMinioDeleteRequest = MicroFastMinioDeleteRequest.builder().bucketName("abucket").objectName("ERP部署文档.doc").deleteMethodTypeEnum(MicroFastMinioDeleteMethodTypeEnum.Object).build();
        fastMinioService.delete(fastMinioDeleteRequest);
    }

    /**
     * 文件查询
     *
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalAccessException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws InternalException
     */
    @GetMapping("list")
    public String list() throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IllegalAccessException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        MicroFastMinioListRequest fastMinioListRequest = MicroFastMinioListRequest.builder().bucketName("abucket").prefix(null).recursive(true).listMethodTypeEnum(MicroFastMinioListMethodTypeEnum.Object).build();
        return JSON.toJSONString(fastMinioService.list(fastMinioListRequest));
    }
}
