package com.asciipic.commands;

import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.InOutCommand;
import com.asciipic.dtos.ResponseDTO;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class AsciiCommand implements InOutCommand {
    private static String ASCII_URL = "http://localhost:9992/images/ascii/";
    private String args;

    public AsciiCommand() {
        args = null;
    }

    public AsciiCommand(String args) {
        this.args = args;
    }

    @Override
    public ResponseDTO execute(List<String> inputStrings) throws CommandError {
        List<String> responseList = new ArrayList<>();
        if (args == null) {
            if(inputStrings == null){
                throw new CommandError("No arguments given!");
            }
            for (String string : inputStrings) {
                String number = string.split("/")[string.split("/").length - 1];
                responseList.add(new RestTemplate().getForObject(ASCII_URL+number, ResponseDTO.class).getContent().get(0));
            }
        } else {
            if(args.matches(" --input=.*/images/[0-9]+")){
                String number = args.split("/")[args.split("/").length - 1];
                responseList.add(new RestTemplate().getForObject(ASCII_URL+number, ResponseDTO.class).getContent().get(0));
            }
        }
        return new ResponseDTO(responseList);
    }
}
