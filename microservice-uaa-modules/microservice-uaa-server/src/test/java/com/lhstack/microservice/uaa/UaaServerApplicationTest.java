package com.lhstack.microservice.uaa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @class: UaaServerApplicationTest
 * @author: lhstack
 * @date:2020/9/15
 * @description:
 * @since: 1.8
 **/
@SpringBootTest
class UaaServerApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    @Test
    void testApplication(){

    }

    @Test
    void testInsertClientJdbcClientDetailsService(){
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
        // baseClientDetails.setAutoApproveScopes(Arrays.asList("web","app"));
        baseClientDetails.setClientId("admin");
        baseClientDetails.setClientSecret("123456");
        baseClientDetails.setResourceIds(Arrays.asList("admin"));
        baseClientDetails.setScope(Arrays.asList("web","app"));
        baseClientDetails.setRegisteredRedirectUri(Collections.singleton("http://localhost:8080/login/sso"));
        clientDetailsService.addClientDetails(baseClientDetails);
        List<ClientDetails> clientDetails = clientDetailsService.listClientDetails();
        clientDetails.forEach(System.out::println);
    }

    @Test
    void testInsertJdbcClientDetailsService(){

        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setAccessTokenValiditySeconds(1800);
        baseClientDetails.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
        baseClientDetails.setAuthorizedGrantTypes(Arrays.asList("password","refresh_token","authentication_code"));
       // baseClientDetails.setAutoApproveScopes(Arrays.asList("web","app"));
        baseClientDetails.setClientId("uaa");
        baseClientDetails.setClientSecret("123456");
        baseClientDetails.setRefreshTokenValiditySeconds(1800);
        baseClientDetails.setResourceIds(Arrays.asList("uaa","article"));
        baseClientDetails.setScope(Arrays.asList("web","app"));
        baseClientDetails.setRegisteredRedirectUri(Collections.singleton("http://localhost:8080/login/sso"));
        clientDetailsService.addClientDetails(baseClientDetails);
        List<ClientDetails> clientDetails = clientDetailsService.listClientDetails();
        clientDetails.forEach(System.out::println);
    }

}
