package com.asciipic.commands;

import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.InOutCommand;
import com.asciipic.dtos.ResponseDTO;

import java.util.ArrayList;
import java.util.List;


public class CommandParser {
    String command;
    List<List<List<String>>> atomicCommands;

    public CommandParser() {
    }

    public CommandParser(String command) {
        this.command = command;
        split();
    }

    private void split() {
        String[] orSplit = command.split(" \\|\\| ");
        atomicCommands = new ArrayList<>();
        for (String orString : orSplit) {
            String[] andSplit = orString.split(" && ");
            List<List<String>> andList = new ArrayList<>();
            for (String andString : andSplit) {
                String[] pipeSplit = andString.split(" \\| ");
                List<String> pipeList = new ArrayList<>();
                for(String pipeString : pipeSplit){
                    pipeList.add(pipeString);
                }
                andList.add(pipeList);
            }
            atomicCommands.add(andList);
        }
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<List<List<String>>> getSplitCommands() {
        return atomicCommands;
    }

    public void setAtomicCommands(List<List<List<String>>> atomicCommands) {
        this.atomicCommands = atomicCommands;
    }

    public static class ExportCommand implements InOutCommand {
        @Override
        public ResponseDTO execute(List<String> inputStrings) throws CommandError {
            return null;
        }
    }
}
