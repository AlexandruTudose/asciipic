package com.asciipic.commands;

import com.asciipic.commands.basic.OutCommand;
import com.asciipic.dtos.ResponseDTO;

public class EchoCommand implements OutCommand {
    private String args;

    public EchoCommand() {
        args = " ";
    }

    public EchoCommand(String args) {
        this.args = args;
    }

    @Override
    public ResponseDTO execute() {
        return new ResponseDTO(args);
    }
}
