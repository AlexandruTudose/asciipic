package com.asciipic.controllers;

import com.asciipic.dtos.GenericResponseDTO;
import com.asciipic.dtos.TerminalPostDTO;
import com.asciipic.services.application.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class RootController {

    @Autowired
    ParserService parserService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GenericResponseDTO> post(@RequestBody TerminalPostDTO terminalPostDTO) {
        System.out.println(terminalPostDTO.getCommand());
        return new ResponseEntity<>(parserService.parse(terminalPostDTO.getCommand()), HttpStatus.OK);
    }
}
