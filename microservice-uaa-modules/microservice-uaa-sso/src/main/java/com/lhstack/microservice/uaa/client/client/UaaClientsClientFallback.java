package com.lhstack.microservice.uaa.client.client;

import feign.hystrix.FallbackFactory;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @class: UaaClientsClientFallback
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@Component
public class UaaClientsClientFallback implements FallbackFactory<UaaClientsClient> {
    @Override
    public UaaClientsClient create(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return new UaaClientsClient() {
            @Override
            public List<BaseClientDetails> list(Integer page, Integer size) {
                return Collections.singletonList(new BaseClientDetails());
            }
        };
    }
}
