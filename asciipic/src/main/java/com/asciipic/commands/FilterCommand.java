package com.asciipic.commands;

import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.InOutCommand;
import com.asciipic.dtos.ImagePostDTO;
import com.asciipic.dtos.ResponseDTO;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class FilterCommand implements InOutCommand {
    private static String IMAGE_METADATA_URL = "http://localhost:9992/images/metadata/";
    private static String FILTER_URL = "http://localhost:9994/filter/";

    private String filterDescription;
    private List<String> inputStrings;

    public FilterCommand() {
        this.filterDescription = null;

    }

    public FilterCommand(String args) throws CommandError {
        inputStrings = new ArrayList<>();
        for (String arg : args.substring(3).split(" --")) {
            if (arg.matches("input=.+,.+")) {
                for (String link : args.substring(6).split(",")) {
                    inputStrings.add(link);
                }
            } else if (arg.matches("name=.+")) {
                this.filterDescription = arg.substring(5);
            } else {
                throw new CommandError("Wrong arguments in search command!");
            }
        }
    }

    @Override
    public ResponseDTO execute(List<String> inputStrings) throws CommandError {
        if (inputStrings != null && this.inputStrings == null) {
            this.inputStrings = inputStrings;
        }

        List<String> responseList = new ArrayList<>();
        try {
            if (this.filterDescription != null) {
                for (String string : inputStrings) {
                    if (string.matches(".*/images/[0-9]+")) {
                        String number = string.split("/")[string.split("/").length - 1];
                        ImagePostDTO imagePostDTO = new RestTemplate().getForObject(IMAGE_METADATA_URL + number, ImagePostDTO.class);
                        System.out.println(FILTER_URL + this.filterDescription);
                        ResponseDTO responseDTO = new RestTemplate().postForObject(FILTER_URL + this.filterDescription, imagePostDTO, ResponseDTO.class);
                        responseList.add(responseDTO.getContent().get(0));
                    } else {
                        responseList.add("Wrong link!");
                    }
                }
                return new ResponseDTO(responseList);
            } else {
                throw new CommandError("Not enough arguments in search command!");
            }
        } catch (HttpClientErrorException e) {
            throw new CommandError("No response! Check link structure!");
        } catch (HttpServerErrorException e) {
            throw new CommandError("No response! Check filter parameters!");
        }
    }
}
