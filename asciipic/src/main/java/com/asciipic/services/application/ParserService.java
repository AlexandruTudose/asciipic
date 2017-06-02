package com.asciipic.services.application;

import com.asciipic.dtos.GenericResponseDTO;


public interface ParserService {
    GenericResponseDTO parse(String command);
}
