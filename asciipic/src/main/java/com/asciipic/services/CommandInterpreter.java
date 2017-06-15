package com.asciipic.services;

import com.asciipic.dtos.ResponseDTO;

import java.util.List;


public interface CommandInterpreter {
    List<ResponseDTO> execute(String command);
}
