package com.lhstack.microservice.uaa.client.client;

import com.lhstack.microservice.common.resp.PageResult;
import com.lhstack.microservice.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @class: UaaClientsClient
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@FeignClient(serviceId = "uaa-resource",fallbackFactory = UaaClientsClientFallback.class)
public interface UaaClientsClient {


    @GetMapping("/client/list/{page}/{size}")
    PageResult<BaseClientDetails> list(@PathVariable("page") Integer page, @PathVariable("size") Integer size);

    @PostMapping("/client/insert")
    Result<BaseClientDetails> insert(@RequestBody BaseClientDetails baseClientDetails);

}