package org.simo.auth.context.provider;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Simeon Popov
 * Date of creation: 6/11/2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestContextInterceptor requestContextInterceptor;

    public WebConfig(RequestContextInterceptor requestContextInterceptor) {
        this.requestContextInterceptor = requestContextInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInterceptor);
    }
}
