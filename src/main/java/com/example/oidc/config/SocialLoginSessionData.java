package com.example.oidc.config;

public record SocialLoginSessionData(
        String provider,
        String subject
) {
    public static final String SESSION_ATTRIBUTE_NAME = "SOCIAL_LOGIN_SESSION_DATA";
}
