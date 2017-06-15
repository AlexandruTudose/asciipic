package com.asciipic.services;

import com.asciipic.commands.*;
import com.asciipic.commands.basic.CommandError;
import com.asciipic.dtos.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommandRetrieverImpl implements CommandRetriever {

    public ResponseDTO getResponse(String command, List<String> inputContent) {
        ResponseDTO response;
        try {
            if (command.matches("echo.*")) {
                if (command.length() == 4) {
                    response = new EchoCommand().execute();
                } else {
                    response = new EchoCommand(command.substring(5)).execute();
                }
            } else if (command.matches("cat.*")) {
                if (command.length() == 3) {
                    response = new CatCommand().execute(inputContent);
                } else {
                    response = new CatCommand(command.substring(4)).execute(inputContent);
                }

            } else if (command.matches("search .*") && command.length() > 7) {
                response = new SearchCommand(command.substring(6)).execute();
            } else if (command.matches("ascii.*")) {
                if (command.length() == 5) {
                    response = new AsciiCommand().execute(inputContent);
                } else {
                    response = new AsciiCommand(command.substring(5)).execute(inputContent);
                }
            } else if (command.matches("job .*")) {
                return new JobCommand(command.substring(4)).execute();
            } else if (command.matches("filter.*")) {
                if (command.length() == 6) {
                    response = new FilterCommand().execute(inputContent);
                } else {
                    response = new FilterCommand(command.substring(6)).execute(inputContent);
                }
            } else {
                throw new CommandError();
            }
        } catch (CommandError e) {
            response = new ResponseDTO(e.toString());
            response.setError();
        }
        return response;
    }
}
