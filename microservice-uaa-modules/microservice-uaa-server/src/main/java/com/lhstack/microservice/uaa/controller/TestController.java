package com.lhstack.microservice.uaa.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @class: TestController
 * @author: lhstack
 * @date:2020/9/15
 * @description:
 * @since: 1.8
 **/
@RestController
@RequestMapping("test")
public class TestController {



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Authentication authentication(@AuthenticationPrincipal Authentication authentication){
        return authentication;
    }
}
