package com.asciipic.login.services;

import com.asciipic.login.dtos.UserLoginDTO;
import com.asciipic.login.models.User;
import com.asciipic.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public String register(User user) throws Exception {

        validateTheInput(user);

        int numberOfEmails = userRepository.findNumberOfEmails(user.getEmail());

        if (numberOfEmails != 0) {
            throw new Exception("Email used!Please try again with another email!");
        }

        User newUser = userRepository.save(user);
        if (newUser == null) {
            throw new Exception("Something went wrong! Please try again!");
        }

        return "Success! Now you are registered to AsciiPic!";
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) throws Exception {

        User user = userRepository.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        if (user == null) {
            throw new Exception("Email or password incorrect! Try again!");
        }

        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    private void validateTheInput(User user) throws Exception {
        if (!user.getUsername().matches("[A-Za-z0-9]+") || user.getUsername().equals("")) {
            throw new Exception("Username not ok! Please try again!");
        }
        if (!user.getPassword().matches("[A-Za-z0-9]+") || user.getPassword().equals("")) {
            throw new Exception("Password not ok! Please try again!");
        }
        if (!user.getEmail().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}") || user.getEmail().equals("")) {
            throw new Exception("Email not valid! Please try again!");
        }
    }


}
