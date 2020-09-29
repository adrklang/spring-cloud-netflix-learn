package com.lhstack.microservice.minio.entity;

import lombok.Data;

/**
 * @author lh
 * @date 2020/9/29
 * @description minio multi upload file use this object
 * @name microservice
 * @sine 11
 */
@Data
public class FileObject {
    private String contentType;

    private String fileName;

    private byte[] content;
}
