package com.lhstack.microservice.uaa.controller;

import com.lhstack.microservice.common.resp.Result;
import com.lhstack.microservice.uaa.service.TokenService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @class: LogoutController
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@RestController
@RequestMapping("oauth")
public class OauthController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("userInfo")
    public ResponseEntity<Authentication> userInfo(@AuthenticationPrincipal Authentication authentication) throws IOException {
        System.out.println(new ObjectMapper().writeValueAsString(authentication));
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("logout")
    public ResponseEntity<Result> logout(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication){
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
        tokenService.removeToken(details.getTokenValue());
        return ResponseEntity.ok(Result.ok());
    }
}
