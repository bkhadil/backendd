package com.system.core.config;

import com.system.core.controller.CorsLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class FilterConfig {

    @Bean
    public CorsLoggingFilter corsLoggingFilter() {
        return new CorsLoggingFilter();
    }
}
