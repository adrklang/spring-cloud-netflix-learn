package com.lhstack.microservice.uaa.client.client;

import com.lhstack.microservice.common.resp.PageResult;
import com.lhstack.microservice.common.resp.Result;
import com.lhstack.microservice.common.resp.ResultEnum;
import feign.hystrix.FallbackFactory;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
            public PageResult<BaseClientDetails> list(Integer page, Integer size) {
                return new PageResult<>(ResultEnum.BAD_REQUEST.setMsg(throwable.getMessage()));
            }

            @Override
            public Result<BaseClientDetails> insert(BaseClientDetails baseClientDetails) {
                return Result.failed(ResultEnum.INTERNAL_SERVER_ERROR.setMsg(throwable.getMessage()),null);
            }
        };
    }
}
