package com.example.oidc.repository;

import com.example.oidc.service.UserSocialLoginEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserSocialLoginRepository {

    @Insert("""
            INSERT INTO user_social_logins (user_id, provider, subject)
            VALUES (#{userId}, #{provider}, #{subject})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(UserSocialLoginEntity entity);

}
