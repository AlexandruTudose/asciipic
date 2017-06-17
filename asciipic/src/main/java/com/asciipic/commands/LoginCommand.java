package com.asciipic.commands;

import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.OutCommand;
import com.asciipic.dtos.ResponseDTO;
import com.asciipic.dtos.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class LoginCommand implements OutCommand {
    private static String LOGIN_URL = "http://localhost:9991/login/";

    private UserLoginDTO userLoginDTO;

    public LoginCommand() {
        this.userLoginDTO = new UserLoginDTO();
    }

    public LoginCommand(String args) throws CommandError {
        this.userLoginDTO = new UserLoginDTO();
        for (String arg : args.substring(3).split(" --")) {
            if (arg.matches("email=.+")) {
                this.userLoginDTO.setEmail(arg.substring(6));
            } else if (arg.matches("password=.+")) {
                this.userLoginDTO.setPassword(arg.substring(9));
            } else {
                throw new CommandError("Invalid credentials!");
            }
        }
    }

    @Override
    public ResponseDTO execute() throws CommandError {
        ResponseEntity<ResponseDTO> responseEntity = new RestTemplate().postForEntity(LOGIN_URL, this.userLoginDTO, ResponseDTO.class);
        System.out.println(responseEntity.getHeaders().get("Authorization"));
        if (responseEntity.getHeaders().get("Authorization") == null) {
            return responseEntity.getBody();
        } else {
            return new ResponseDTO(responseEntity.getHeaders().get("Authorization"));
        }
    }
}
