package org.simo.auth.context.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author: Simeon Popov
 * Date of creation: 6/19/2024
 */
@ExtendWith(MockitoExtension.class)
public class UserContextFilterTest {

    @Mock
    private WebFilterChain webFilterChain;

    @InjectMocks
    private UserContextFilter userContextFilter;

    @BeforeEach
    public void setUp() {
        RequestContextHolder.clear();
    }

    @Test
    public void testFilterWithUserContext() {
        // Arrange
        MockServerHttpRequest request = MockServerHttpRequest.get("/")
                .header("X-User-Email", "test@example.com")
                .header("X-User-Role", "USER")
                .build();

        ServerWebExchange exchange = MockServerWebExchange.from(request);

        when(webFilterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> filterResult = userContextFilter.filter(exchange, webFilterChain);

        // Assert: Verify the context before the request completes
        filterResult = filterResult.doOnSuccess(aVoid -> {
            RequestContext context = RequestContextHolder.getContext();
            assertAll(
                    () -> assertEquals("test@example.com", context.getUserEmail(), "User email should be set in context"),
                    () -> assertEquals("USER", context.getUserRole(), "User role should be set in context")
            );
        });

        // Verify the Mono completes as expected and then clear the context
        StepVerifier
                .create(filterResult)
                .verifyComplete();

        verify(webFilterChain).filter(exchange);
    }

    @Test
    public void testFilterWithoutUserContext() {
        // Arrange
        MockServerHttpRequest request = MockServerHttpRequest.get("/").build();
        ServerWebExchange exchange = MockServerWebExchange.from(request);
        when(webFilterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> filterResult = userContextFilter.filter(exchange, webFilterChain);

        // Assert: Verify the context before the request completes
        filterResult = filterResult.doOnSuccess(aVoid -> {
            RequestContext context = RequestContextHolder.getContext();
            assertAll(
                    () -> assertNull(context.getUserEmail(), "User email should not be set in context"),
                    () -> assertNull(context.getUserRole(), "User role should not be set in context")
            );
        });

        // Verify the Mono completes as expected and then clear the context
        StepVerifier
                .create(filterResult)
                .verifyComplete();

        verify(webFilterChain).filter(exchange);
    }

    @Test
    public void testFilterClearsContext() {
        // Arrange
        MockServerHttpRequest request = MockServerHttpRequest.get("/")
                .header("X-User-Email", "test@example.com")
                .header("X-User-Role", "USER")
                .build();

        ServerWebExchange exchange = MockServerWebExchange.from(request);

        when(webFilterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> filterResult = userContextFilter.filter(exchange, webFilterChain);

        // Assert: Verify the context before the request completes
        filterResult = filterResult.doOnSuccess(aVoid -> {
            RequestContext context = RequestContextHolder.getContext();
            assertAll(
                    () -> assertEquals("test@example.com", context.getUserEmail(), "User email should be set in context"),
                    () -> assertEquals("USER", context.getUserRole(), "User role should be set in context")
            );
        });

        // Verify the Mono completes as expected and then clear the context
        StepVerifier
                .create(filterResult)
                .verifyComplete();

        // After the Mono completes, verify the context is cleared
        RequestContext clearedContext = RequestContextHolder.getContext();
        assertAll(
                () -> assertNull(clearedContext.getUserEmail(), "User email should be cleared from context"),
                () -> assertNull(clearedContext.getUserRole(), "User role should be cleared from context")
        );

        verify(webFilterChain).filter(exchange);
    }
}