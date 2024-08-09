package com.example.oidc.service;

import com.example.oidc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void register(String username) {
        var newUser = new UserEntity(null, username);
        userRepository.insert(newUser);
    }

}
