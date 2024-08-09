package com.example.oidc.repository;

import com.example.oidc.service.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    @Insert("""
            INSERT INTO users (username) VALUES (#{username})
            """)
    void insert(UserEntity entity);
}
