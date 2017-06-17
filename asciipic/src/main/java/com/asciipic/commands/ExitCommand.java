package com.asciipic.commands;

import com.asciipic.dtos.ResponseDTO;
import org.springframework.web.client.RestTemplate;

public class ExitCommand {
    private static String TOKEN_URL = "http://localhost:9991/logout?token=";

    private String token;

    public ExitCommand(String token) {
        this.token = token;
    }

    public ResponseDTO execute() {
        return new RestTemplate().getForEntity(TOKEN_URL + this.token, ResponseDTO.class).getBody();
    }
}
