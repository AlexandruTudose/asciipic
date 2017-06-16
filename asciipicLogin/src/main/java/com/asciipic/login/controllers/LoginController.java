package com.asciipic.login.controllers;

import com.asciipic.login.dtos.ResponseDTO;
import com.asciipic.login.dtos.UserDTO;
import com.asciipic.login.dtos.UserLoginDTO;
import com.asciipic.login.models.User;
import com.asciipic.login.models.UserToken;
import com.asciipic.login.services.UserService;
import com.asciipic.login.services.UserTokenService;
import com.asciipic.login.transformers.UserTransformer;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {


    @Autowired
    UserService userService;

    @Autowired
    UserTokenService userTokenService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> resizeImage(@RequestBody UserDTO userDTO) {
        UserTransformer userTransformer = new UserTransformer();
        User user = userTransformer.toModel(userDTO);

        String response = null;
        try {
            response = userService.register(user);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseDTO(true, response), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO,
                                             HttpServletRequest headers) {
        User user = new User();
        UserToken userToken = new UserToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            user = userService.login(userLoginDTO);
            userToken = userTokenService.makeToken(user.getId());

            httpHeaders.set("Authorization", userToken.getToken());
            httpHeaders.set("Username", user.getUsername());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage()), httpHeaders, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new ResponseDTO(true, "Success! Now you are logged in to AsciiPic!"), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getUserByToken(@RequestParam String token) {
        UserToken userToken = userTokenService.getUserTokenByToken(token);
        System.out.println("1");
        if(userToken == null){
            return  new ResponseEntity<>(new ResponseDTO(false, "User not logged in!"), HttpStatus.OK);
        }
        System.out.println("2");
        User user = userService.getUserById(userToken.getIdUser());
        List<String> list = new ArrayList<>();
        list.add(user.getEmail());
        list.add(String.valueOf(user.getId()));
        return new ResponseEntity<>(new ResponseDTO(true,list), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDTO> validateSession(HttpServletRequest headers) {
        HttpHeaders httpHeaders = new HttpHeaders();

        String token = headers.getHeader("Authorization");
        UserToken userToken = userTokenService.getUserTokenByToken(token);

        if (userToken == null || token == null) {
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(false, "You're not logged in! Please log in!"), httpHeaders, HttpStatus.UNAUTHORIZED);
        }

        UserToken newUserToken = userTokenService.makeToken(userToken.getIdUser());
        User user = userService.getUserById(newUserToken.getIdUser());
        httpHeaders.set("Authorization", newUserToken.getToken());
        httpHeaders.set("Username", user.getUsername());

        return new ResponseEntity<ResponseDTO>(new ResponseDTO(true, "Success!"), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> logout(HttpServletRequest headers) {
        HttpHeaders httpHeaders = new HttpHeaders();

        String token = headers.getHeader("Authorization");
        UserToken userToken = userTokenService.getUserTokenByToken(token);

        if (userToken == null || token == null) {
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(false, "You're not logged in! Please log in!"), httpHeaders, HttpStatus.UNAUTHORIZED);
        }

        userTokenService.deleteUserTokenByToken(token);

        return new ResponseEntity<>(new ResponseDTO(true, "Success! Now you are logged out from AsciiPic!"), httpHeaders, HttpStatus.OK);
    }
}
