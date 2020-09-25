package com.lhstack.microservice.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @class: UaaServerApplication
 * @author: lhstack
 * @date:2020/9/15
 * @description:
 * @since: 1.8
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class UaaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaServerApplication.class,args);
    }
}
