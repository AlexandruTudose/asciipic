package com.asciipic.commands.basic;


import com.asciipic.dtos.ResponseDTO;

public interface OutCommand {
    ResponseDTO execute() throws CommandError;
}
