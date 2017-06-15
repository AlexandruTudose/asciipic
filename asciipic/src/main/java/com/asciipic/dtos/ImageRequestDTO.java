package com.asciipic.dtos;

import com.asciipic.commands.SearchCommand;

import java.io.Serializable;
import java.util.List;


public class ImageRequestDTO implements Serializable {
    private String source;
    private String postDate;
    private String size;
    private List<String> tags;
    private long number;

    public ImageRequestDTO() {
    }

    public ImageRequestDTO(SearchCommand searchCommand) {
        this.source = searchCommand.getSource();
        this.postDate = searchCommand.getDate();
        this.size = searchCommand.getSize();
        this.tags = searchCommand.getTags();
        this.number = searchCommand.getNumber();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
