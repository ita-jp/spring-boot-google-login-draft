package com.example.oidc.service;

import com.example.oidc.repository.UserRepository;
import com.example.oidc.repository.UserSocialLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSocialLoginRepository userSocialLoginRepository;

    @Transactional
    public UserEntity register(String username, String provider, String subject) {
        var newUser = new UserEntity(null, username);
        userRepository.insert(newUser);

        var newUserSocialLogin = new UserSocialLoginEntity(null, newUser.getId(), provider, subject);
        userSocialLoginRepository.insert(newUserSocialLogin);

        return newUser;
    }

}
