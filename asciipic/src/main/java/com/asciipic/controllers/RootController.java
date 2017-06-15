package com.asciipic.controllers;

import com.asciipic.dtos.ResponseDTO;
import com.asciipic.dtos.TerminalPostDTO;
import com.asciipic.services.CommandInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class RootController {

    @Autowired
    CommandInterpreter commandInterpreter;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<ResponseDTO>> post(@RequestBody TerminalPostDTO terminalPostDTO) {
        System.out.println(terminalPostDTO.getCommand());
        return new ResponseEntity<>(commandInterpreter.execute(terminalPostDTO.getCommand()), HttpStatus.OK);
    }
}
