package com.lhstack.microservice.oauth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    @GetMapping("info")
    public Object info(@AuthenticationPrincipal OAuth2AuthenticationToken authentication){
        return authentication;
    }

    /**
     * 退出登录
     * @param authentication
     */
    @GetMapping("logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) throws IOException {


    }
}
