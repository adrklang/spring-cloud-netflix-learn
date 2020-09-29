package com.lhstack.microservice.minio.service;

import com.lhstack.microservice.common.resp.Result;
import com.lhstack.microservice.common.resp.ResultEnum;
import com.lhstack.microservice.common.util.DateUtils;
import com.lhstack.microservice.common.util.IOUtils;
import com.lhstack.microservice.minio.entity.FileObject;
import com.lhstack.microservice.minio.props.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lh
 * @date 2020/9/29
 * @description
 * @name microservice
 * @sine 11
 */
@AllArgsConstructor
@Slf4j
public class MinioUploadService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;


    /**
     * 删除bucket
     * @param bucket
     * @return
     */
    public boolean deleteBucket(String bucket){
        try {
            this.minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.error("exception [{}]",e.getMessage());
            return false;
        }
    }


    /**
     * 检查bucket是否存在
     * @param bucket
     * @return
     */
    public boolean bucketExists(String bucket) {
        try {
            return this.minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("exception [{}]",e.getMessage());
            return false;
        }
    }

    /**
     * 创建bucket
     * @param bucket
     * @return
     * @throws Exception
     */
    public boolean createBucket(String bucket){
        try {
            this.minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.error("exception [{}]",e.getMessage());
            return false;
        }
    }


    /**
     * 删除object
     * @param bucket
     * @param object
     * @return
     */
    public boolean deleteObject(String bucket,String object){
        try {
            this.minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(object).build());
            return true;
        } catch (Exception e) {
            log.error("exception [{}]",e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param bucket
     * @param objects
     * @return
     */
    public boolean deleteObjects(String bucket, List<DeleteObject> objects){
        try {
            this.minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucket).objects(objects).build());
            return true;
        } catch (Exception e) {
            log.error("exception [{}]",e.getMessage());
            return false;
        }
    }

    /**
     * 默认签名url 五分钟，get请求
     * @param bucket
     * @param fileName
     * @return
     */
    public String getDefaultSignObjectUrl(String bucket, String fileName){
        return getSignObjectUrl(bucket,fileName,Method.GET,5,TimeUnit.MINUTES);
    }

    /**
     * 生成签名的url
     * @param bucket 桶名称
     * @param fileName objectName
     * @param method 允许请求方法
     * @param expireTime 过期时间，配合timeUnit
     * @param timeUnit 时间单位
     * @return
     */
    public String getSignObjectUrl(String bucket, String fileName, Method method, Integer expireTime, TimeUnit timeUnit){

        try {
            return this.minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(method)
                            .expiry(expireTime, timeUnit)
                            .bucket(bucket)
                            .object(fileName).build());
        } catch (Exception e) {
            return String.format("[%s] null",e.getMessage());
        }
    }

    /**
     * 通过bucket和fileName获取url
     * @param bucket
     * @param fileName
     * @return
     */
    public String getObjectUrl(String bucket,String fileName){
        try {
            return minioClient.getObjectUrl(bucket,fileName);
        } catch (Exception e) {
            return String.format("[%s] null",e.getMessage());
        }
    }

    /**
     * 上传文件，使用默认的bucketName
     * @param fileName
     * @param in
     * @param contentType
     * @return
     */
    public Result<Map<String,Object>> uploadFile(String fileName, InputStream in, String contentType) {
        return this.uploadFile(fileName, in,contentType,this.minioProperties.getDefaultBucketName());
    }

    /**
     * 多文件上传
     * @param objects
     * @param bucketName
     * @return
     */
    public List<Result<Map<String,Object>>> uploads(List<FileObject> objects,String bucketName){
        return objects.stream().map(item -> this.uploadFile(item.getFileName(), item.getContent(), item.getContentType(),bucketName)).collect(Collectors.toList());
    }

    /**
     * 上传文件，使用自定义的bucketName
     * @param fileName
     * @param in
     * @param contentType
     * @param bucketName
     * @return
     */
    public Result<Map<String,Object>> uploadFile(String fileName, InputStream in, String contentType, String bucketName) {
        byte[] bytes = null;
        try {
            bytes = IOUtils.ioToBytes(in);
        } catch (IOException e) {
            return Result.failed(ResultEnum.INTERNAL_SERVER_ERROR.setMsg(e.getMessage()),
                    Map.of(
                            "fileSize",0,
                            "fileName",fileName,
                            "timestamp",DateUtils.getTimestamp()));
        }
        return this.uploadFile(fileName, bytes,contentType,bucketName);
    }

    /**
     *
     * @param fileName
     * @param content
     * @param contentType
     * @param bucketName
     * @return
     */
    public Result<Map<String,Object>> uploadFile(String fileName,byte[] content,String contentType,String bucketName) {
        PutObjectArgs putObjectArgs = PutObjectArgs
                .builder()
                .contentType(contentType)
                .object(fileName)
                .bucket(bucketName)
                .stream(new ByteArrayInputStream(content), content.length, PutObjectOptions.MAX_PART_SIZE)
                .userMetadata(Map.of("server", "microservice", "timestamp", String.valueOf(DateUtils.getTimestamp())))
                .build();
        try{
            ObjectWriteResponse objectWriteResponse = this.minioClient.putObject(putObjectArgs);
            String bucket = objectWriteResponse.bucket();
            String object = objectWriteResponse.object();
            String objectUrl = this.getDefaultSignObjectUrl(bucket,object);
            return Result.ok(Map.of(
                    "fileSize",content.length,
                    "fileName",fileName,
                    "url",objectUrl,
                    "timestamp",DateUtils.getTimestamp()));
        }catch (Exception e){
            return Result.failed(ResultEnum.INTERNAL_SERVER_ERROR.setMsg(e.getMessage()),
                    Map.of(
                            "fileSize",content.length,
                            "fileName",fileName,
                            "timestamp",DateUtils.getTimestamp()));
        }
    }

    /**
     * 上传文件
     * @param fileName
     * @param content
     * @param contentType
     * @return
     */
    public Result<Map<String,Object>> uploadFile(String fileName,byte[] content,String contentType) {
        return uploadFile(fileName,content,contentType,this.minioProperties.getDefaultBucketName());
    }


    /**
     * 如果不存在则创建
     * @param bucket
     * @throws Exception
     */
    public void ifNotCreateBucket(String bucket) throws Exception {
        if(!this.bucketExists(bucket)){
            this.createBucket(bucket);
        }
    }
}
