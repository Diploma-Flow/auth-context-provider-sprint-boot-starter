package org.simo.auth.context.provider;


/**
 * Author: Simeon Popov
 * Date of creation: 6/11/2024
 */

public class RequestContext{
    private String userEmail;
    private String userRole;

    public RequestContext() {
    }

    public RequestContext(String userEmail, String userRole) {
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
