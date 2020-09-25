package com.lhstack.microservice.uaa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @class: ClientsService
 * @author: lhstack
 * @date:2020/9/21
 * @description:
 * @since: 1.8
 **/
@Service
public class ClientsService {

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public ClientDetails insert(ClientDetails clientDetails){
        jdbcClientDetailsService.addClientDetails(clientDetails);
        return clientDetails;
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public ClientDetails findById(String id){
        return jdbcClientDetailsService.loadClientByClientId(id);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void remove(String clientId){
        jdbcClientDetailsService.removeClientDetails(clientId);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public ClientDetails update(ClientDetails clientDetails){
        jdbcClientDetailsService.updateClientDetails(clientDetails);
        return clientDetails;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void updateClientSecret(String clientId,String clientSecret){
        jdbcClientDetailsService.updateClientSecret(clientId,clientSecret);
    }




    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Page<ClientDetails> findByPage(Integer page,Integer size){
        long count = jdbcTemplate.query("SELECT COUNT( * ) AS total FROM oauth_client_details", rs -> {
            long total = 0;
            while (rs.next()) {
                return rs.getLong("total");
            }
            return total;
        });
        if(page < 1){
            page = 1;
        }
        page = (page - 1) * size;
        List<ClientDetails> details = jdbcTemplate.query("SELECT client_id,client_secret,resource_ids, scope,authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove FROM oauth_client_details LIMIT ?,?", new Object[]{page, size}, (RowMapper<ClientDetails>) (rs, rowNum) -> {
            BaseClientDetails baseClientDetails = new BaseClientDetails();
            String resourceIds = rs.getString("resource_ids");
            String scope = rs.getString("scope");
            String authorizedGrantTypes = rs.getString("authorized_grant_types");
            String webServerRedirectUri = rs.getString("web_server_redirect_uri");
            String authorities = rs.getString("authorities");
            int accessTokenValidity = rs.getInt("access_token_validity");
            int refreshTokenValidity = rs.getInt("refresh_token_validity");
            String autoapprove = rs.getString("autoapprove");
            String additionalInformation = rs.getString("additional_information");
            String clientId = rs.getString("client_id");
            String clientSecret = rs.getString("client_secret");
            baseClientDetails.setClientId(clientId);
            baseClientDetails.setClientSecret(clientSecret);
            try {
                Map<String, Object> map = objectMapper.readValue(additionalInformation, new TypeReference<Map<String, Object>>() {
                });
                baseClientDetails.setAdditionalInformation(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                baseClientDetails.setAdditionalInformation(new HashMap<>(0));
            }

            baseClientDetails.setAutoApproveScopes(Arrays.asList(autoapprove.split(",")));
            baseClientDetails.setRefreshTokenValiditySeconds(refreshTokenValidity);
            baseClientDetails.setAccessTokenValiditySeconds(accessTokenValidity);
            baseClientDetails.setAuthorities(Arrays.stream(authorities.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
            baseClientDetails.setRegisteredRedirectUri(Arrays.stream(webServerRedirectUri.split(",")).collect(Collectors.toSet()));
            baseClientDetails.setAuthorizedGrantTypes(Arrays.asList(authorizedGrantTypes.split(",")));
            baseClientDetails.setScope(Arrays.asList(scope.split(",")));
            baseClientDetails.setResourceIds(Arrays.asList(resourceIds.split(",")));
            return baseClientDetails;
        });

        return new PageImpl<>(details, PageRequest.of(page + 1,size),count);
    }

}
