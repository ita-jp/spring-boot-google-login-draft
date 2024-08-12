package com.example.oidc.service;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class CurrentUser extends DefaultOidcUser {

    private final UserEntity userEntity;

    public CurrentUser(OidcUser oidcUser, UserEntity userEntity) {
        super(oidcUser.getAuthorities(), oidcUser.getIdToken());
        this.userEntity = userEntity;
    }

}
