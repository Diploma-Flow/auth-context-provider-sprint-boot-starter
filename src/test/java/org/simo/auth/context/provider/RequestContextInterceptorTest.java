package org.simo.auth.context.provider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Author: Simeon Popov
 * Date of creation: 6/19/2024
 */

@ExtendWith(MockitoExtension.class)
class RequestContextInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @InjectMocks
    private RequestContextInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        RequestContextHolder.clear();
    }

    @Test
    public void testPreHandle() {
        // Arrange
        when(request.getHeader("X-User-Email")).thenReturn("test@example.com");
        when(request.getHeader("X-User-Role")).thenReturn("USER");

        // Act
        boolean result = interceptor.preHandle(request, response, handler);

        // Assert
        RequestContext context = RequestContextHolder.getContext();
        assertAll(
                () -> assertTrue(result, "preHandle should return true"),
                () -> assertEquals("test@example.com", context.getUserEmail(), "User email should be set in context"),
                () -> assertEquals("USER", context.getUserRole(), "User role should be set in context")
        );
    }

    @Test
    public void testAfterCompletion() {
        // Arrange
        RequestContextHolder.getContext().setUserEmail("test@example.com");
        RequestContextHolder.getContext().setUserRole("USER");

        // Act
        interceptor.afterCompletion(request, response, handler, null);

        // Assert
        RequestContext context = RequestContextHolder.getContext();

        assertAll(
                () -> assertNull(context.getUserEmail(), "User email should be cleared from context"),
                () -> assertNull(context.getUserRole(), "User role should be cleared from context")
        );
    }
}