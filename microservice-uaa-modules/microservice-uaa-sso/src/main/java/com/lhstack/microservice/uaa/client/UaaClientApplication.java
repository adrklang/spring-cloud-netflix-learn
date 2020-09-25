package com.lhstack.microservice.uaa.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @class: UaaClientApplication
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@SpringCloudApplication
@EnableFeignClients(basePackages = "com.lhstack.microservice.uaa.client.client")
@EnableOAuth2Sso
public class UaaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaClientApplication.class,args);
    }
}
