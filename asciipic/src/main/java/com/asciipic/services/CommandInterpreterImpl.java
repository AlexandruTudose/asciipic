package com.asciipic.services;

import com.asciipic.commands.*;
import com.asciipic.dtos.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommandInterpreterImpl implements CommandInterpreter {

    @Autowired
    CommandRetriever commandRetriever;

    @Override
    public List<ResponseDTO> execute(String command) {
        List<List<List<String>>> splitCommands = new CommandParser(command).getSplitCommands();
        List<ResponseDTO> responses = new ArrayList<>();
        List<String> inputContent;
        for (int i = 0; i < splitCommands.size(); i++) {
            if (i > 0 && responses.get(responses.size() - 1).getMetadata().isSuccessful()) {
                break;
            }
            for (int j = 0; j < splitCommands.get(i).size(); j++) {
                if (j > 0 && !responses.get(responses.size() - 1).getMetadata().isSuccessful()) {
                    break;
                }
                for (int k = 0; k < splitCommands.get(i).get(j).size(); k++) {
                    if (k > 0) {
                        inputContent = responses.get(responses.size() - 1).getContent();
                        responses.remove(responses.get(responses.size() - 1));
                    } else {
                        inputContent = null;
                    }
                    command = splitCommands.get(i).get(j).get(k);
                    responses.add(commandRetriever.getResponse(command, inputContent));
                }
            }
        }
        return responses;
    }


}
