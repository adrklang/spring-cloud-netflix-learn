package com.lhstack.microservice.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @class: UaaResourceServerApplication
 * @author: lhstack
 * @date:2020/9/21
 * @description:
 * @since: 1.8
 **/
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
public class UaaResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UaaResourceServerApplication.class,args);
    }
}
