package com.asciipic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asciipic.dtos.ResponseDTO;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Terminal {
    private static String CORE_API = "http://localhost:9989/api";
    private static String PROMPT = "@asciipic ~$ ";
    private boolean running;
    private Scanner scanner;
    private String token;
    private String username;
    List<String> commandHistory;


    public Terminal() {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.token = null;
        this.username = "guest";
        this.commandHistory = new ArrayList<>();
    }

    public void start() {
        while (running) {
            try {
                printResult(interpretCommand(getInputCommand()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printResult(List<ResponseDTO> responseDTOs) {
        for (ResponseDTO responseDTO : responseDTOs) {
            if (responseDTO.getMetadata().isSuccessful()) {
                for (String contentLine : responseDTO.getContent())
                    System.out.println(contentLine);
            } else {
                System.out.println("Error: " + responseDTO.getContent().get(0));
            }
        }
    }

    private String getInputCommand() {
        System.out.printf(this.username + PROMPT);
        String stringCommand = this.scanner.nextLine();
        this.commandHistory.add(stringCommand);
        return stringCommand;
    }

    private List<ResponseDTO> interpretCommand(String command) throws IOException {
        if (command.matches("exit")) {
            this.running = false;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(new JSONObject().put("command", command).toString(), headers);
        String jsonStringResponse = new RestTemplate().postForEntity(this.CORE_API, entity, String.class).getBody();
        return new ObjectMapper().readValue(jsonStringResponse, new TypeReference<List<ResponseDTO>>() {
        });
    }
}
