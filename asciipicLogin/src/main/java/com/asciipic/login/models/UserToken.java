package com.asciipic.login.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "user_token")
public class UserToken implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long idUser;

    @NotNull
    @Column(name = "token", length = 2048)
    private String token;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
