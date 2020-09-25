package com.lhstack.microservice.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @class: EurekaServerApplicationTests
 * @author: lhstack
 * @date:2020/9/15
 * @description:
 * @since: 1.8
 **/
@SpringBootTest
class EurekaServerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testApplication(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
    }
}
