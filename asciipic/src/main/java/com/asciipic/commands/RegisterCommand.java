package com.asciipic.commands;

import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.OutCommand;
import com.asciipic.dtos.ResponseDTO;
import com.asciipic.dtos.UserDTO;
import org.springframework.web.client.RestTemplate;


public class RegisterCommand implements OutCommand {
    private static String REGISTER_URL = "http://localhost:9991/register/";

    private UserDTO userDTO;

    public RegisterCommand() {
        this.userDTO = new UserDTO();
    }

    public RegisterCommand(String args) throws CommandError {
        this.userDTO = new UserDTO();
        for (String arg : args.substring(3).split(" --")) {
            if (arg.matches("email=.+")) {
                this.userDTO.setEmail(arg.substring(6));
            } else if (arg.matches("password=.+")) {
                this.userDTO.setPassword(arg.substring(9));
            } else if (arg.matches("username=.+")) {
                this.userDTO.setUsername(arg.substring(9));
            } else {
                throw new CommandError("Invalid credentials!");
            }
        }
    }

    @Override
    public ResponseDTO execute() throws CommandError {
        ResponseDTO responseDTO = new RestTemplate().postForObject(REGISTER_URL, this.userDTO, ResponseDTO.class);
        return responseDTO;
    }
}
