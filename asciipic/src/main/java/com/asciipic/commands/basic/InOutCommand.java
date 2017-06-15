package com.asciipic.commands.basic;


import com.asciipic.dtos.ResponseDTO;

import java.util.List;

public interface InOutCommand {
    ResponseDTO execute(List<String> inputStrings) throws CommandError;
}
