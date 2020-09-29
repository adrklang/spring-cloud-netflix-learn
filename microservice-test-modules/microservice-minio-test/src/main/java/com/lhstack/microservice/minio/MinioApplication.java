package com.lhstack.microservice.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lh
 * @date 2020/9/29
 * @description
 * @name microservice
 * @sine 11
 */
@SpringBootApplication
public class MinioApplication implements ApplicationRunner {

    @Autowired
    private MinioClient minioClient;

    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class,args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(minioClient);
    }
}
