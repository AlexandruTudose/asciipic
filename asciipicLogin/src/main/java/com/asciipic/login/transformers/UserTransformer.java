package com.asciipic.login.transformers;

import com.asciipic.login.dtos.UserDTO;
import com.asciipic.login.models.User;

public class UserTransformer {
    public User toModel(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }
}
