package com.asciipic.journalize.dtos.postDto;

import java.io.Serializable;

public class JournalizeRegisterPostDTO implements Serializable{
    private String ip;
    private long userId;

    public JournalizeRegisterPostDTO() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
