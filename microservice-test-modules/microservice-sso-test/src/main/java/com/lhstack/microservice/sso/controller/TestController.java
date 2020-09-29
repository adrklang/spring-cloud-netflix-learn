package com.lhstack.microservice.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @class: TestController
 * @author: lhstack
 * @date:2020/9/16
 * @description:
 * @since: 1.8
 **/
@RestController
@RequestMapping
public class TestController {

    @Value("${security.oauth2.client.logout-uri}")
    private String logoutUri;

    @GetMapping("logout")
    public void logout(@AuthenticationPrincipal Authentication authentication, HttpSession session, HttpServletResponse response) throws IOException {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        session.invalidate();
        SecurityContextHolder.clearContext();
        String tokenValue = details.getTokenValue();
        response.sendRedirect(String.format("%s?access_token=%s",logoutUri,tokenValue));
    }


    @GetMapping("oauth/token_receive")
    public void tokenReceive(String code){
        System.out.println(code);
    }

    @GetMapping("oauth/info")
    public Authentication authentication(@AuthenticationPrincipal Authentication authentication){
        return authentication;
    }
}
