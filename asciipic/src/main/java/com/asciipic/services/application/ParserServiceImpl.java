package com.asciipic.services.application;

import com.asciipic.dtos.GenericResponseDTO;
import org.springframework.stereotype.Service;


@Service
public class ParserServiceImpl implements ParserService {

    @Override
    public GenericResponseDTO parse(String command) {

        if(command.matches("echo .*")){
            return new GenericResponseDTO<String, String>(null, command.substring(5));

        }
        else{
            return new GenericResponseDTO<String, String>(null, "Command unknown.");
        }
    }
}
