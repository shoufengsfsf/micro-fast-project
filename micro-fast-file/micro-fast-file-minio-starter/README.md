# 文件存储模块使用说明

## 简介

文件存储模块主要基于[minio](https://min.io/)实现（不肯买oss这类的对象存储服务可以用它），它兼容亚马逊S3云存储服务接口，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几kb到最大5T不等。

## 使用说明

具体使用案例请查看：[micro-fast-demo-file](https://github.com/shoufengsfsf/micro-fast-project/tree/master/micro-fast-demo/micro-fast-demo-file)

### 1、通过docker-compose.yml启动minio

```java
version: '3.1'
services:
  minio1:
    image: minio/minio
    ports:
      - 9001:9000
    environment:
      MINIO_ACCESS_KEY: adminA123
      MINIO_SECRET_KEY: 12345Aadmin
    volumes:
      - /Users/shoufeng/Desktop/minio/data:/data
      - /Users/shoufeng/Desktop/minio/config:/root/.minio
    command: server /data
```



### 2、添加依赖

```java
<dependency>
    <groupId>com.shoufeng.project</groupId>
    <artifactId>micro-fast-file-minio-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 3、修改application.yml配置

```java
micro:
  fast:
    minio:
      enable: true
      endpoint: http://127.0.0.1:9001/
      accessKey: adminA123
      secretKey: 12345Aadmin
```

### 4、注入封装后的文件MicroFastMinioService

```java
//原声
@Resource
private MinioClient minioClient;

//封装
@Resource
private MicroFastMinioService fastMinioService;
```

### 5、MicroFastMinioService上传、下载、删除、查询接口均有提供，上传下载默认返回的是有效期为7天的下载链接。

```java
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
```

上传接口提供两种上传方式

```java
/**
 * 上传方式枚举
 *
 * @author shoufeng
 */

public enum MicroFastMinioUploadMethodTypeEnum {
    /**
     * 本地文件
     */
    LOCAL,
    /**
     * 流
     */
    STREAM,
    ;
}
```

下载接口提供两种下载方式

```java
/**
 * 下载方式枚举
 *
 * @author shoufeng
 */

public enum MicroFastMinioDownloadMethodTypeEnum {
    /**
     * 直接下载
     */
    DIRECT,
    /**
     * 下载链接
     */
    URL,
    ;
}
```

查询接口提供两种查询方式

```java
/**
 * 查询方式枚举
 *
 * @author shoufeng
 */

public enum MicroFastMinioListMethodTypeEnum {
    /**
     * 查询存储桶
     */
    Bucket,
    /**
     * 查询对象
     */
    Object,
    ;
}
```

删除接口提供两种删除方式

```java
/**
 * 删除方式枚举
 *
 * @author shoufeng
 */

public enum MicroFastMinioDeleteMethodTypeEnum {
    /**
     * 删除一个存储桶
     */
    Bucket,
    /**
     * 删除一个对象
     */
    Object,
    ;
}
```

### 另外说明

有钱的话还是建议买存储服务，项目经理一直不肯批的话，minio还是比较好用的。