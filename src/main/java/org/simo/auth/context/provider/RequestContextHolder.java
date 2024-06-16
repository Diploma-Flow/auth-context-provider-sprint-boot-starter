package org.simo.auth.context.provider;

/**
 * Author: Simeon Popov
 * Date of creation: 6/11/2024
 */
public class RequestContextHolder {
    private static final ThreadLocal<RequestContext> userContext = ThreadLocal.withInitial(RequestContext::new);

    public static RequestContext getContext() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}
