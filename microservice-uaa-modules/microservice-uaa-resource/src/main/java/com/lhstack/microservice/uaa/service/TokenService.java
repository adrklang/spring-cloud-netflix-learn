package com.lhstack.microservice.uaa.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * @class: TokenService
 * @author: lhstack
 * @date:2020/9/25
 * @description:
 * @since: 1.8
 **/
@Service
public class TokenService {

    private JdbcTokenStore tokenStore;

    public TokenService(DataSource dataSource){
        this.tokenStore = new JdbcTokenStore(dataSource);
    }

    /**
     * 移除token
     * @param token
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void removeToken(String token){
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        if(oAuth2AccessToken != null){
            tokenStore.removeAccessToken(oAuth2AccessToken.getValue());
            OAuth2RefreshToken refreshToken = oAuth2AccessToken.getRefreshToken();
            if(refreshToken != null){
                tokenStore.removeRefreshToken(refreshToken.getValue());
            }
        }

    }
}
