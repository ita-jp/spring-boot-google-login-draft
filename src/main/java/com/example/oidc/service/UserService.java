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
    public void register(String username) {
        var newUser = new UserEntity(null, username);
        userRepository.insert(newUser);

        var newUserSocialLogin = new UserSocialLoginEntity(null, newUser.getId(), "google", "12345");
        userSocialLoginRepository.insert(newUserSocialLogin);
    }

}
