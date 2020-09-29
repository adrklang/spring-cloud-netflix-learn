package com.lhstack.microservice.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @class: ReactiveOauthLoginConfiguration
 * @author: lhstack
 * @date:2020/9/18
 * @description:
 * @since: 1.8
 **/
@EnableWebFluxSecurity
public class ReactiveOauthLoginConfiguration  {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
        return httpSecurity.authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2Login()
                .authenticationSuccessHandler((webFilterExchange, authentication) -> new DefaultServerRedirectStrategy().sendRedirect(webFilterExchange.getExchange(), URI.create("/oauth/info")))
                .clientRegistrationRepository(createClientRegistrationRepository()).and().build();
    }

    @Bean
    public ReactiveClientRegistrationRepository createClientRegistrationRepository() {
        return new InMemoryReactiveClientRegistrationRepository(getRegistration());
    }

    private List<ClientRegistration> getRegistration() {
        Map<String,Object> map = new HashMap<>();
        map.put("logoutUri","http://uaa.lhstack.com:9720/oauth2/logout");
        return Arrays.asList(ClientRegistration.withRegistrationId("sso")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://uaa.lhstack.com:9720/oauth/authorize")
                .clientId("uaa-client")
                .clientSecret("123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                .providerConfigurationMetadata(map)
                .scope("web")
                .tokenUri("http://uaa.lhstack.com:9720/oauth/token")
                .userInfoUri("http://uaa.lhstack.com:9720/auth/info")
                .userInfoAuthenticationMethod(AuthenticationMethod.QUERY)
                .userNameAttributeName("name")
                .build(), ClientRegistration.withRegistrationId("oss")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://uaa.lhstack.com:9720/oauth/authorize")
                .clientId("zuul-gateway")
                .clientSecret("123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .redirectUriTemplate("http://gateway.lhstack.com:9999/login")
                .scope("web")
                .tokenUri("http://uaa.lhstack.com:9720/oauth/token")
                .userInfoUri("http://uaa.lhstack.com:9720/oauth/check_token")
                .userNameAttributeName("user")
                .build());
    }
}
