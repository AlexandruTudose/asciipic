package com.asciipic.services;

import com.asciipic.dtos.ResponseDTO;

import java.util.List;


public interface CommandRetriever {
    ResponseDTO getResponse(String command, List<String> inputContent);
}
