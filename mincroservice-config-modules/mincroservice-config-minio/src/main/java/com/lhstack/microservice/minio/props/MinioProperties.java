package com.lhstack.microservice.minio.props;

import lombok.Data;

/**
 * @author lh
 * @date 2020/9/29
 * @description minio properties
 * @name microservice
 * @sine 11
 */
@Data
public class MinioProperties {

    /**
     * minio endpoint 端点
     * example
     * <pre>
     * https://s3.amazonaws.com
     * https://s3.amazonaws.com/
     * https://play.min.io
     * http://play.min.io:9010/
     * localhost
     * localhost.localdomain
     * play.min.io
     * 127.0.0.1
     * 192.168.1.60
     * ::1
     * </pre>
     */
    private String endpoint;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * accessSecret
     */
    private String accessSecret;

    /**
     * default region
     */
    private String region = "";

    /**
     * default bucket name
     */
    private String defaultBucketName = "default";

}
