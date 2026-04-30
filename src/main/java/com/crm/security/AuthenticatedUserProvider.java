package com.crm.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@ApplicationScoped
public class AuthenticatedUserProvider {

    @Inject
    JsonWebToken jsonWebToken;

    public UUID getCurrentAuthUserId() {
        if (jsonWebToken == null || jsonWebToken.getSubject() == null) {
            throw new IllegalStateException("Authenticated user not found in token");
        }
        return UUID.fromString(jsonWebToken.getSubject());
    }

    public String getCurrentRole() {
        Object claim = jsonWebToken.getClaim("role");
        return claim != null ? claim.toString() : null;
    }

    public String getCurrentEmail() {
        Object claim = jsonWebToken.getClaim("email");
        return claim != null ? claim.toString() : null;
    }
}