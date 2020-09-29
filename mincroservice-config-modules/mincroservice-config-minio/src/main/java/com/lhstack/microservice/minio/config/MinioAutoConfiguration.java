package com.lhstack.microservice.minio.config;

import com.lhstack.microservice.minio.props.MinioProperties;
import com.lhstack.microservice.minio.service.MinioUploadService;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author lh
 * @date 2020/9/29
 * @description 自动配置minio
 * @name microservice
 * @sine 11
 */
@ConditionalOnProperty(name = "com.lhstack.minio.enable",havingValue = "true",matchIfMissing = false)
public class MinioAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "com.lhstack.minio")
    public MinioProperties minioProperties(){
        return new MinioProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioClient minioClient(MinioProperties minioProperties){
        MinioClient.Builder builder = new MinioClient.Builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getAccessSecret());
        if(!minioProperties.getRegion().isEmpty()){
            builder.region(minioProperties.getRegion());
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnBean(MinioClient.class)
    public MinioUploadService minioUploadService(MinioClient minioClient,MinioProperties minioProperties){
        return new MinioUploadService(minioClient,minioProperties);
    }
}
