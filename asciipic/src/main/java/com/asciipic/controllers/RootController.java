package com.asciipic.controllers;

import com.asciipic.commands.ExitCommand;
import com.asciipic.commands.RenewTokenHeader;
import com.asciipic.commands.LoginCommand;
import com.asciipic.commands.RegisterCommand;
import com.asciipic.commands.basic.CommandError;
import com.asciipic.dtos.ResponseDTO;
import com.asciipic.dtos.TerminalPostDTO;
import com.asciipic.services.CommandInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RootController {

    @Autowired
    CommandInterpreter commandInterpreter;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<ResponseDTO>> post(@RequestBody TerminalPostDTO terminalPostDTO, @RequestHeader(value = "Authorization") String header) {
        System.out.println(terminalPostDTO.getCommand());
        List<ResponseDTO> responseDTOs = new ArrayList<>();
        if (header.matches("null")) {
            if (terminalPostDTO.getCommand().matches("login.+")) {
                ResponseDTO responseDTO;
                try {
                    responseDTO = new LoginCommand(terminalPostDTO.getCommand().substring(5)).execute();
                } catch (CommandError commandError) {
                    responseDTO = new ResponseDTO(commandError.toString());
                    responseDTO.setError();
                    responseDTOs.add(responseDTO);
                    return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
                }
                if (responseDTO.getMetadata().isSuccessful()) {
                    responseDTOs.add(new ResponseDTO("Authentication successful!"));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.set("Authorization", responseDTO.getContent().get(0));
                    return new ResponseEntity<>(responseDTOs, httpHeaders, HttpStatus.OK);
                } else {
                    responseDTOs.add(responseDTO);
                    return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
                }
            } else if (terminalPostDTO.getCommand().matches("register.+")) {
                try {
                    responseDTOs.add(new RegisterCommand(terminalPostDTO.getCommand().substring(8)).execute());
                    return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
                } catch (CommandError commandError) {
                    ResponseDTO responseDTO = new ResponseDTO(commandError.toString());
                    responseDTO.setError();
                    responseDTOs.add(responseDTO);
                    return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
                }
            } else {
                responseDTOs.add(new ResponseDTO("Authentication required!"));
                return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
            }
        } else if (terminalPostDTO.getCommand().matches("logout")) {
            responseDTOs.add(new ExitCommand(header).execute());
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } else {
            HttpHeaders httpHeaders = new RenewTokenHeader(header).execute();
            return new ResponseEntity<>(commandInterpreter.execute(terminalPostDTO.getCommand()), httpHeaders, HttpStatus.OK);
        }
    }
}
