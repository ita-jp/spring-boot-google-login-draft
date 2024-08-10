package com.example.oidc.config;

import com.example.oidc.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Authentication ANONYMOUS_AUTHENTICATION = new AnonymousAuthenticationToken(
            "anonymous",
            "anonymousUser",
            AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
    );

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        var sessionData = buildSocialLoginSessionData(authentication);
        if (isRegisteredUser(sessionData.provider(), sessionData.subject())) {
            response.sendRedirect("/");
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(ANONYMOUS_AUTHENTICATION);
        request.getSession().setAttribute(
                SocialLoginSessionData.SESSION_ATTRIBUTE_NAME,
                sessionData
        );
        response.sendRedirect("/register-profile");
    }

    private boolean isRegisteredUser(String provider, String subject) {
        return userRepository.selectBySubject(provider, subject).isPresent();
    }

    private SocialLoginSessionData buildSocialLoginSessionData(
            Authentication authentication
    ) {
        String clientRegistrationId = "";
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            clientRegistrationId = oauth2Token.getAuthorizedClientRegistrationId();
        }

        String subject = "";
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            subject = oidcUser.getSubject();
        }

        return new SocialLoginSessionData(clientRegistrationId, subject);
    }
}
