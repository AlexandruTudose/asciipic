package com.asciipic.commands;

import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.OutCommand;
import com.asciipic.dtos.ResponseDTO;
import org.springframework.web.client.RestTemplate;

public class JobCommand implements OutCommand {
    private static String JOB_URL = "http://localhost:9993/crawls/";

    private String id;

    public JobCommand(String args) throws CommandError {
        if (args.matches("--id=[0-9]+")) {
            this.id = args.substring(5);
        } else {
            throw new CommandError("Wrong arguments in job command!");
        }
    }


    @Override
    public ResponseDTO execute() {
        return new RestTemplate().getForObject(JOB_URL + id, ResponseDTO.class);
    }
}
