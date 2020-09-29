package com.lhstack.microservice.sso.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.Filter;

/*
*
 * @class: PostProcessor
 * @author: lhstack
 * @date:2020/9/17
 * @description:
 * @since: 1.8
 *
*/

@Configuration
public class PostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FilterChainProxy) {

            FilterChainProxy chains = (FilterChainProxy) bean;

            for (SecurityFilterChain chain : chains.getFilterChains()) {
                for (Filter filter : chain.getFilters()) {
                    if (filter instanceof OAuth2ClientAuthenticationProcessingFilter) {
                        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter = (OAuth2ClientAuthenticationProcessingFilter) filter;
                        oAuth2ClientAuthenticationProcessingFilter
                                .setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/oauth/info"));
                    }
                }
            }
        }
        return bean;
    }
}
