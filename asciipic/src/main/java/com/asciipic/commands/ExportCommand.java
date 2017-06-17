package com.asciipic.commands;

import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.InOutCommand;
import com.asciipic.dtos.ImagePostDTO;
import com.asciipic.dtos.ResponseDTO;
import com.asciipic.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExportCommand implements InOutCommand {
    private static String IMAGE_METADATA_URL = "http://localhost:9992/images/metadata/";
    private static String FILTER_URL = "http://localhost:9994/filter/";
    private static String SUBJECT = "AsciiPic Image and Metadata export";

    @Autowired
    EmailService emailService;

    private String mode;
    private String format;
    private String email;
    private List<String> inputStrings;

    public ExportCommand() {
        setDefaultValues();
        this.inputStrings = null;
    }

    public ExportCommand(String args) throws CommandError {
        setArgs(args);
    }

    public void setArgs(String args) throws CommandError {
        setDefaultValues();
        inputStrings = new ArrayList<>();
        for (String arg : args.substring(3).split(" --")) {
            if (arg.matches("input=.+,.+")) {
                for (String link : args.substring(6).split(",")) {
                    inputStrings.add(link);
                }
            } else if (arg.matches("mode=.+")) {
                this.mode = arg.substring(5);
            } else if (arg.matches("format=.+")) {
                this.mode = arg.substring(7);
            } else {
                throw new CommandError("Wrong arguments in export command!");
            }
        }

    }


    private void setDefaultValues() {
        this.email = "eualex2304@gmail.com";
        this.format = "json";
        this.mode = "local";
    }

    @Override
    public ResponseDTO execute(List<String> inputStrings) throws CommandError {
        if (inputStrings != null && this.inputStrings == null) {
            this.inputStrings = inputStrings;
        }
        List<String> responseList = new ArrayList<>();
        for (String string : inputStrings) {
            if (string.matches(".*/images/[0-9]+")) {
                String number = string.split("/")[string.split("/").length - 1];

                ImagePostDTO imagePostDTO = new RestTemplate().getForObject(IMAGE_METADATA_URL + number, ImagePostDTO.class);
                //System.out.println(imagePostDTO.toString());
                responseList.add(imagePostDTO.toString());
            } else {
                responseList.add("Wrong link!");
            }
        }
        if (this.mode.matches("email")) {
            System.out.println(responseList.toString());
            emailService.send(this.email, this.SUBJECT, responseList.toString());
            return new ResponseDTO("Email sent!");
        } else if (this.mode.matches("local")) {

            return new ResponseDTO("File downloaded!");
        } else {
            throw new CommandError("Wrong mode in export command. Please choose from local or email.");
        }
    }

}
