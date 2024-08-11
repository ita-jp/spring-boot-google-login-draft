package com.example.oidc.service;

import com.example.oidc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(
            OidcUserRequest userRequest
    ) throws OAuth2AuthenticationException {
        var oidcUser = super.loadUser(userRequest);

        return userRepository.selectBySubject(
                        userRequest.getClientRegistration().getRegistrationId(),
                        oidcUser.getSubject()
                )
                .map(userEntity -> new CurrentUser(oidcUser, userEntity))
                .orElseGet(() -> new CurrentUser(oidcUser, null));
    }
}
