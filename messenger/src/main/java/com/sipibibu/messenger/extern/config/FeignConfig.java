package com.sipibibu.messenger.extern.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    RequestInterceptor feignRequestInterceptor(){
        return  requestTemplate -> requestTemplate.header(
                "Authorization",
                "Bearer "+SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getCredentials()
                        .toString()
        );
}
}
