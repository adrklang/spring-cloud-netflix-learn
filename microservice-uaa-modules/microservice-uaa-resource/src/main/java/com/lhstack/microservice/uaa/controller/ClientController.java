package com.lhstack.microservice.uaa.controller;

import com.lhstack.microservice.common.resp.PageResult;
import com.lhstack.microservice.common.resp.Result;
import com.lhstack.microservice.uaa.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @class: ClientController
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientsService clientsService;


    @PostMapping("insert")
    public ResponseEntity<Result<ClientDetails>> insertClient(@RequestBody BaseClientDetails baseClientDetails){
        return ResponseEntity.ok(Result.ok(clientsService.insert(baseClientDetails)));
    }

    @DeleteMapping("del/{clientId}")
    public ResponseEntity<Result> removeClient(@PathVariable("clientId") String clientId){
        clientsService.remove(clientId);
        return ResponseEntity.ok(Result.ok());
    }

    @PutMapping("update")
    public ResponseEntity<Result<ClientDetails>> updateClient(@RequestBody BaseClientDetails baseClientDetails){
        ClientDetails update = clientsService.update(baseClientDetails);
        return ResponseEntity.ok(Result.ok(update));
    }

    @GetMapping("list/{page}/{size}")
    public ResponseEntity<PageResult<ClientDetails>> list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) throws InterruptedException {
        return ResponseEntity.ok(clientsService.findByPage(page, size));
    }
}
