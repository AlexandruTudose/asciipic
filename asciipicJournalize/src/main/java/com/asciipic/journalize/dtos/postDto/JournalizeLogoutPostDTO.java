package com.asciipic.journalize.dtos.postDto;

import java.io.Serializable;
import java.security.SecureRandom;

public class JournalizeLogoutPostDTO implements Serializable{
    private long userId;
    private String cause;

    public JournalizeLogoutPostDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
