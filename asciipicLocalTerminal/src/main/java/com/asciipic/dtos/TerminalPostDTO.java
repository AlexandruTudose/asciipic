package com.asciipic.dtos;


import java.io.Serializable;

public class TerminalPostDTO implements Serializable {
    String command;

    public TerminalPostDTO() {
    }

    public TerminalPostDTO(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
