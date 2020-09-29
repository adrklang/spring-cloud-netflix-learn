package com.lhstack.microservice.admin.config;

import com.lhstack.microservice.common.resp.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @class: SecurityConfig
 * @author: lhstack
 * @date:2020/9/27
 * @description:
 * @since: 1.8
 **/
@EnableWebFluxSecurity
public class SecurityConfig {

  /*  @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(){
        UserDetails userDetails = AttributeAuthority.withUsername("admin")
                .password("{noop}123456")
                .roles("ADMIN").build();
        return new MapReactiveUserDetailsService(userDetails);
    }*/

  private RestTemplate restTemplate = new RestTemplate();

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
      return httpSecurity
              .authorizeExchange()
              .anyExchange()
              .authenticated()
              .and()
              .csrf()
              .disable()
              .logout()
              .logoutSuccessHandler((webFilterExchange, authentication) -> {
                  if(authentication instanceof OAuth2AuthenticationToken){
                      OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                      OAuth2User principal = oAuth2AuthenticationToken.getPrincipal();
                      LinkedHashMap details = principal.getAttribute("details");
                      Result result = restTemplate.getForObject(URI.create("http://uaa.lhstack.com:9725/oauth/logout?access_token=" + details.get("tokenValue")), Result.class);
                  }
                  Mono<WebSession> session = webFilterExchange.getExchange().getSession();
                  session.doOnNext(WebSession::invalidate).subscribe().dispose();
                  DefaultServerRedirectStrategy defaultServerRedirectStrategy = new DefaultServerRedirectStrategy();
                  return defaultServerRedirectStrategy.sendRedirect(webFilterExchange.getExchange(), URI.create("http://uaa.lhstack.com:9720/logout"));
              })
              .and()
              .oauth2Login()
              .clientRegistrationRepository(createClientRegistrationRepository())
              .and()
              .build();
    }

    @Bean
    public ReactiveClientRegistrationRepository createClientRegistrationRepository() {
        return new InMemoryReactiveClientRegistrationRepository(getRegistration());
    }

    private List<ClientRegistration> getRegistration() {
        return Arrays.asList(ClientRegistration.withRegistrationId("admin")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://uaa.lhstack.com:9720/oauth/authorize")
                .clientId("admin")
                .clientSecret("123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("web")
                .tokenUri("http://uaa.lhstack.com:9720/oauth/token")
                .userInfoUri("http://uaa.lhstack.com:9725/oauth/userInfo")
                .userNameAttributeName("name")
                .build());
    }
}
