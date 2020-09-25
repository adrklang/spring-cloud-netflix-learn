package com.lhstack.microservice.uaa.controller;

import com.lhstack.microservice.uaa.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.List;

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
    public ResponseEntity<ClientDetails> insertClient(@RequestBody BaseClientDetails baseClientDetails){
        return ResponseEntity.ok(clientsService.insert(baseClientDetails));
    }

    @DeleteMapping("del/{clientId}")
    public ResponseEntity<Boolean> removeClient(@PathVariable("clientId") String clientId){
        clientsService.remove(clientId);
        return ResponseEntity.ok(true);
    }

    @PutMapping("update")
    public ResponseEntity<ClientDetails> updateClient(@RequestBody BaseClientDetails baseClientDetails){
        ClientDetails update = clientsService.update(baseClientDetails);
        return ResponseEntity.ok(update);
    }

    @GetMapping("list/{page}/{size}")
    public ResponseEntity<List<ClientDetails>> list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) throws InterruptedException {
        System.out.println(page);
        System.out.println(size);
        Page<ClientDetails> pageResult = clientsService.findByPage(page, size);
        System.out.println(pageResult);
        return ResponseEntity.ok(pageResult.getContent());
    }
}
