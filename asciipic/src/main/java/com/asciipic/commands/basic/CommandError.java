package com.asciipic.commands.basic;

public class CommandError extends Exception {
    public CommandError() {
        super("Command parse error!");
    }

    public CommandError(String s) {
        super(s);
    }
}
