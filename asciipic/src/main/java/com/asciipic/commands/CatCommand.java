package com.asciipic.commands;


import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.InOutCommand;
import com.asciipic.dtos.ResponseDTO;

import java.util.List;

public class CatCommand implements InOutCommand {
    private String args;

    public CatCommand() {
        args = null;
    }

    public CatCommand(String args) {
        this.args = args;
    }

    @Override
    public ResponseDTO execute(List<String> inputStrings) throws CommandError {
        ResponseDTO response = new ResponseDTO();
        if (inputStrings != null && this.args == null) {
            for (String inputString : inputStrings) {
                response.addContent(inputString);
            }
            return response;
        } else {
            throw new CommandError(String.format("File '%s' not found.", args));
        }
    }
}
