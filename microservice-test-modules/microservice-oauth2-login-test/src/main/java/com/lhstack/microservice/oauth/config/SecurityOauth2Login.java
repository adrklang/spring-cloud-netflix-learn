package com.lhstack.microservice.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @class: SecurityOauth2Login
 * @author: lhstack
 * @date:2020/9/16
 * @description:
 * @since: 1.8
 **/
@EnableWebSecurity
@Configuration
public class SecurityOauth2Login extends WebSecurityConfigurerAdapter {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth2/**","/login/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .logout()
                .disable()
//                .oauth2Client()
                .oauth2Login()
                .failureUrl("/oauth/failure")
                .defaultSuccessUrl("/oauth/info",true)//配置授权成功之后重定向地址
                .clientRegistrationRepository(getOauthClient())
                .permitAll();
    }

    @Bean
    public ClientRegistrationRepository getOauthClient() {
        return new InMemoryClientRegistrationRepository(getRegistration());
    }

    private List<ClientRegistration> getRegistration() {
        Map<String,Object> map = new HashMap<>();
        map.put("logoutUri","http://uaa.lhstack.com:9720/oauth2/logout");
        return Arrays.asList(ClientRegistration.withRegistrationId("sso")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://uaa.lhstack.com:9720/oauth/authorize")
                .clientId("uaa")
                .clientSecret("123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                .providerConfigurationMetadata(map)
                .scope("web")
                .tokenUri("http://uaa.lhstack.com:9720/oauth/token")
                .userInfoUri("http://uaa.lhstack.com:9725/oauth/userInfo")
                .userInfoAuthenticationMethod(AuthenticationMethod.QUERY)
                .userNameAttributeName("name")
                .build(), ClientRegistration.withRegistrationId("oss")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://uaa.lhstack.com:9720/oauth/authorize")
                .clientId("zuul-gateway")
                .clientSecret("123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .redirectUriTemplate("http://gateway.lhstack.com:9999/login")
                .scope("web")
                .tokenUri("http://uaa.lhstack.com:9720/oauth/token")
                .userInfoUri("http://uaa.lhstack.com:9720/oauth/check_token")
                .userNameAttributeName("user")
                .build());
    }
}
