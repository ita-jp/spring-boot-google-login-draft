package com.example.oidc.config;

import com.example.oidc.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserRegistrationRedirectFilter extends GenericFilterBean {

    private final UserRepository userRepository;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            var provider = oauth2Token.getAuthorizedClientRegistrationId();
            var subject = oauth2Token.getName();
            if (userRepository.selectBySubject(provider, subject).isEmpty()
                    && !req.getRequestURI().equals("/register-profile")
            ) {
                res.sendRedirect("/register-profile");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
