package com.asciipic.journalize.dtos.postDto;

import java.io.Serializable;

public class JournalizeLoginPostDTO implements Serializable{
    private long userId;
    private String ip;
    private String userAgent;

    public JournalizeLoginPostDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
