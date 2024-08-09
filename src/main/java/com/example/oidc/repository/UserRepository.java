package com.example.oidc.repository;

import com.example.oidc.service.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserRepository {

    @Insert("""
            INSERT INTO users (username) VALUES (#{username})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(UserEntity entity);
}
