package com.lhstack.microservice.uaa.client.controller;

import com.lhstack.microservice.uaa.client.client.UaaClientsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @class: UaaClientsController
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@RestController
@RequestMapping("client")
@Api(tags = "uaa clients manager")
public class UaaClientsController {

    @Autowired
    private UaaClientsClient clientsClient;

    @GetMapping("list/{page}/{size}")
    @ApiOperation("分页查询客户端")
    public ResponseEntity<List<BaseClientDetails>> list(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return ResponseEntity.ok(clientsClient.list(page,size));
    }
}
