package com.asciipic.dtos;

import java.io.Serializable;

public class UserLoginDTO implements Serializable{
    private String email;
    private String password;


    public UserLoginDTO() {
        this.email = null;
        this.password = null;
    }

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
