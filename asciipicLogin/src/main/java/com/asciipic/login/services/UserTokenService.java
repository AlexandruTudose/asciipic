package com.asciipic.login.services;

import com.asciipic.login.models.UserToken;

public interface UserTokenService {

    UserToken makeToken(Long id);

    UserToken getUserTokenByToken(String token);

    void deleteUserTokenByToken(String token);
}
