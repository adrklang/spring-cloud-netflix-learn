package com.lhstack.microservice.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @class: EurekaSecurityConfiguration
 * @author: lhstack
 * @date:2020/9/15
 * @description: 配置服务注册安全控制
 * @since: 1.8
 **/
@EnableWebSecurity
@Configuration
public class EurekaSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}123456")
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/health","/actuator/info")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()//关掉csrf配置
                .httpBasic();//开启httpBasic认证配置//
    }
}
