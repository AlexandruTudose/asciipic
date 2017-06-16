package com.asciipic.journalize.transformers;

import com.asciipic.journalize.dtos.UserDTO;
import com.asciipic.journalize.models.User;

public class UserTransformer {
    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    public User toModel(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        return user;
    }
}
