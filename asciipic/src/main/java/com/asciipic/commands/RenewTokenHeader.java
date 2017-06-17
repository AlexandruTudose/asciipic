package com.asciipic.commands;

import com.asciipic.dtos.ResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class RenewTokenHeader {
    private static String TOKEN_URL = "http://localhost:9991/token?token=";

    private String token;

    public RenewTokenHeader(String token) {
        this.token = token;
    }

    public HttpHeaders execute() {
        ResponseEntity<ResponseDTO> responseEntity = new RestTemplate().getForEntity(TOKEN_URL + this.token, ResponseDTO.class);
        return responseEntity.getHeaders();
    }
}
