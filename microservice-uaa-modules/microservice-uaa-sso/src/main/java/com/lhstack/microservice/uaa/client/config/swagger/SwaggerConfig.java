package com.lhstack.microservice.uaa.client.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @class: SwaggerConfig
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@EnableSwagger2
@EnableKnife4j
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.lhstack.microservice.uaa.client.controller")).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .version("0.0.1")
                .title("uaa-sso-client")
                .description("uaa sso client")
                .build();
    }
}
