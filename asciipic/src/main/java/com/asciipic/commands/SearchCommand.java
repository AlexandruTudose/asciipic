package com.asciipic.commands;


import com.asciipic.commands.basic.CommandError;
import com.asciipic.commands.basic.OutCommand;
import com.asciipic.dtos.ImageRequestDTO;
import com.asciipic.dtos.ResponseDTO;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class SearchCommand implements OutCommand {
    private static String SEARCH_URL = "http://localhost:9992/searches";
    private static String CRAWL_URL = "http://localhost:9993/crawls";
    private List<String> tags;
    private String size;
    private String date;
    private String source;
    private long number;
    private boolean isCrawl;

    public SearchCommand(String stringArgs) throws CommandError {
        setDefaultValues();
        for (String arg : stringArgs.substring(3).split(" --")) {
            if (arg.matches("tags=.*")) {
                for (String tag : arg.substring(5).split(",")) {
                    this.tags.add(tag);
                }
            } else if (arg.matches("size=large") || arg.matches("size=medium") ||
                    arg.matches("size=small") || arg.matches("size=original")) {
                this.size = arg.substring(5);
            } else if (arg.matches("post_date=[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]")) {
                this.date = arg.substring(10);
            } else if (arg.matches("source=flickr")) {
                this.source = "flickr";
            } else if (arg.matches("number=[0-9]+")) {
                this.number = Long.parseLong(arg.substring(7));
            } else if (arg.matches("crawl=true") || arg.matches("crawl=false")) {
                if (arg.matches("crawl=true")){
                    this.isCrawl = true;
                }
            } else {
                throw new CommandError("Wrong arguments in search command!");
            }
        }
    }

    private void setDefaultValues() {
        this.source = "flickr";
        this.date = "";
        this.size = "small";
        this.tags = new ArrayList<>();
        this.number = 1;
        this.isCrawl = false;
    }

    @Override
    public ResponseDTO execute() {
        ImageRequestDTO imageRequestDTO = new ImageRequestDTO(this);
        ResponseDTO responseDTO;
        if(isCrawl){
            if(imageRequestDTO.getPostDate().matches("")){
                imageRequestDTO.setPostDate(null);
            }
            responseDTO = new RestTemplate().postForObject(CRAWL_URL, imageRequestDTO, ResponseDTO.class);
        }
        else{
            responseDTO = new RestTemplate().postForObject(SEARCH_URL, imageRequestDTO, ResponseDTO.class);
        }
        return responseDTO;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isCrawl() {
        return isCrawl;
    }

    public void setCrawl(boolean crawl) {
        isCrawl = crawl;
    }

    @Override
    public String toString() {
        return "SearchCommand{" +
                "tags=" + tags +
                ", size='" + size + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
