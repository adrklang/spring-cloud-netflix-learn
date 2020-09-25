package com.lhstack.microservice.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @class: Oauth2Controller
 * @author: lhstack
 * @date:2020/9/16
 * @description:
 * @since: 1.8
 **/
@RestController
@RequestMapping("oauth")
public class Oauth2Controller {

    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;


    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("info")
    public Object info(@AuthenticationPrincipal OAuth2AuthenticationToken authentication, HttpServletRequest request){
        return authentication;
    }

    /**
     * 退出登录
     * @param authentication
     * @param request
     * @param response
     */
    @GetMapping("logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(@AuthenticationPrincipal OAuth2AuthenticationToken authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth2AuthorizedClient auth2AuthorizedClient = oAuth2AuthorizedClientRepository.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication, request);
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        if(auth2AuthorizedClient != null){
            ClientRegistration clientRegistration = auth2AuthorizedClient.getClientRegistration();
            String logoutUri = String.format("%s?access_token=%s", clientRegistration.getProviderDetails().getConfigurationMetadata().get("logoutUri"), auth2AuthorizedClient.getAccessToken().getTokenValue());
            System.out.println(logoutUri);
            Map forObject = restTemplate.getForObject(logoutUri, Map.class);
            System.out.println(forObject);
            response.sendRedirect("/");
        }
    }
}
