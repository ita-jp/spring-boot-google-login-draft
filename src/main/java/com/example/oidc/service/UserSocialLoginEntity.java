package com.example.oidc.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSocialLoginEntity {

    private Long id;
    private Long userId;
    private String provider;
    private String subject;

}
