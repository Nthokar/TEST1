package com.nthokar.spring2023.userauth.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class FeignConfig {

    @Bean
    RequestInterceptor feignRequestInterceptor(){
        return  requestTemplate -> requestTemplate.header(
                "Authorization",
                "Bearer "+ ((Jwt) SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getCredentials())
                        .getTokenValue()
        );
}
}
