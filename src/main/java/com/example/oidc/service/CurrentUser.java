package com.example.oidc.service;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class CurrentUser extends DefaultOidcUser {

    private final UserEntity user;

    public CurrentUser(OidcUser oidcUser, UserEntity userEntity) {
        super(oidcUser.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo());
        this.user = userEntity;
    }

    public String getUsername() {
        if (user == null) {
            throw new IllegalStateException("Current user does not have username.");
        }
        return user.getUsername();
    }

    public boolean hasCompletedUserRegistration() {
        return user != null;
    }
}
