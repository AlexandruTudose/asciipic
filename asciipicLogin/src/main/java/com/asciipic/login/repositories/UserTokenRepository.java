package com.asciipic.login.repositories;

import com.asciipic.login.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    @Transactional
    @Modifying
    @Query("update com.asciipic.login.models.UserToken set token = ?1 where user_id = ?2")
    void updateToken(String token, Long id);

    @Transactional
    @Modifying
    void deleteByToken(String token);

    UserToken findByToken(String token);

    @Query("select t from com.asciipic.login.models.UserToken t where user_id = ?1")
    UserToken getUserTokenById(Long id);
}