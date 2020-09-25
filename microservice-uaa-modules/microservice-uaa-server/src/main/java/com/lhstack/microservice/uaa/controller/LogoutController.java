package com.lhstack.microservice.uaa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhstack.microservice.uaa.config.TokenServices;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @class: LogoutController
 * @author: lhstack
 * @date:2020/9/16
 * @description:
 * @since: 1.8
 **/
@Controller
@RequestMapping("oauth2/logout")
public class LogoutController {

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public void logout(
            @AuthenticationPrincipal Authentication authentication,HttpServletResponse res, HttpSession session) throws IOException {

        if(authentication instanceof OAuth2Authentication){
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
            System.out.println(oAuth2Authentication);
            clearAuthorization(session);
            String redirectUri = oAuth2Request.getRedirectUri();
            writeSuccess("退出登录成功", redirectUri, res);
        }
    }

    private void writeSuccess(String message, String redirectUri, HttpServletResponse res) throws IOException {
        ServletOutputStream outputStream = res.getOutputStream();
        res.setHeader("Content-Type", "application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>(3);
        map.put("redirect_uri", redirectUri);
        map.put("status", "200");
        map.put("ok", true);
        map.put("message", message);
        objectMapper.writeValue(outputStream, map);
        outputStream.close();
    }

    private void clearAuthorization(HttpSession session) {
        SecurityContextHolder.clearContext();
        session.invalidate();
    }

    private void writeError(String message, HttpServletResponse res) throws IOException {
        ServletOutputStream outputStream = res.getOutputStream();
        res.setHeader("Content-Type", "application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>(3);
        map.put("status", "500");
        map.put("ok", false);
        map.put("message", message);
        objectMapper.writeValue(outputStream, map);
        outputStream.close();
    }
}
