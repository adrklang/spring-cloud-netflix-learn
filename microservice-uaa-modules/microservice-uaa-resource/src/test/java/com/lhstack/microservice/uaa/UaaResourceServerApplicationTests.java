package com.lhstack.microservice.uaa;

import com.lhstack.microservice.common.resp.PageResult;
import com.lhstack.microservice.uaa.service.ClientsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.List;

/**
 * @class: UaaResourceServerApplicationTests
 * @author: lhstack
 * @date:2020/9/21
 * @description:
 * @since: 1.8
 **/
@SpringBootTest
class UaaResourceServerApplicationTests {

    @Autowired
    private ClientsService clientsService;

    @Test
    void testFindByPageClient(){
        PageResult<ClientDetails> byPage = clientsService.findByPage(2, 2);

    }
}
