package com.example.oidc.repository;

import com.example.oidc.service.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserRepository {

    @Insert("""
            INSERT INTO users (username) VALUES (#{username})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(UserEntity entity);

    @Select("""
            SELECT
                u.id
              , u.username
            FROM users u
            JOIN user_social_logins usl ON u.id = usl.user_id
            WHERE
                usl.subject = #{subject}
            """)
    Optional<UserEntity> selectBySubject(String provider, String subject);
}
