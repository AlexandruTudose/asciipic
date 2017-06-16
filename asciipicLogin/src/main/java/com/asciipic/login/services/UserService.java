package com.asciipic.login.services;

import com.asciipic.login.dtos.UserLoginDTO;
import com.asciipic.login.models.User;

public interface UserService {
    String register(User user) throws Exception;

    User login(UserLoginDTO userLoginDTO) throws Exception;

    User getUserById(Long id);


}
