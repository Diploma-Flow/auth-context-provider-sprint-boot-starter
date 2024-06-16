package org.simo.auth.context.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Author: Simeon Popov
 * Date of creation: 6/11/2024
 */

@Component
public class UserContextFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.debug("Entering UserContextFilter.filter");

        String userEmail = exchange.getRequest().getHeaders().getFirst("X-User-Email");
        String userRole = exchange.getRequest().getHeaders().getFirst("X-User-Role");

        RequestContext requestContext = RequestContextHolder.getContext();
        requestContext.setUserEmail(userEmail);
        requestContext.setUserRole(userRole);

        log.debug("Added context for user: {}",userEmail);

        return chain.filter(exchange).doFinally(signalType -> {
            RequestContextHolder.clear();
            log.debug("Cleared user context");
        });
    }
}
