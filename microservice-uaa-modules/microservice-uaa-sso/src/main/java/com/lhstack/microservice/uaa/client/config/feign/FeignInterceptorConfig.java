package com.lhstack.microservice.uaa.client.config.feign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

/**
 * @class: FeignInterceptorConfig
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@Configuration
public class FeignInterceptorConfig {

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> requestTemplate.header("Authorization","Bearer " + oAuth2ClientContext.getAccessToken().getValue());
    }

}
