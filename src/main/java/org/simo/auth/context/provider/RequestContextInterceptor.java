package org.simo.auth.context.provider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Author: Simeon Popov
 * Date of creation: 6/11/2024
 */

@Component
public class RequestContextInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestContextInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.trace("Entering RequestContextInterceptor.preHandle");

        String userEmail = request.getHeader("X-User-Email");
        String userRole = request.getHeader("X-User-Role");

        RequestContext requestContext = RequestContextHolder.getContext();
        requestContext.setUserEmail(userEmail);
        requestContext.setUserRole(userRole);
        log.debug("Added context for user: {}",userEmail);

        return true; // Continue processing the request
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.trace("Entering RequestContextInterceptor.afterCompletion");

        RequestContextHolder.clear();
        log.debug("Cleared user context");
    }
}
