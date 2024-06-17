package org.simo.auth.context.configuration;

import org.junit.jupiter.api.Test;
import org.simo.auth.context.provider.RequestContextInterceptor;
import org.simo.auth.context.provider.UserContextFilter;
import org.simo.auth.context.provider.WebConfig;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Simeon Popov
 * Date of creation: 6/17/2024
 */
class RequestContextAutoConfigurationTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(RequestContextAutoConfiguration.class));

    @Test
    void shouldContain_RequestContextInterceptor_bean(){
        runner.run(context -> {
            assertNotNull(context.getBean(RequestContextInterceptor.class));
            assertTrue(context.containsBean("requestContextInterceptor"));
        });
    }

    @Test
    void shouldContain_UserContextFilter_bean(){
        runner.run(context -> {
            assertNotNull(context.getBean(UserContextFilter.class));
            assertTrue(context.containsBean("userContextFilter"));
        });
    }

    @Test
    void shouldContain_WebConfig_bean(){
        runner.run(context -> {
            assertNotNull(context.getBean(WebConfig.class));
            assertTrue(context.containsBean("webConfig"));
        });
    }

}