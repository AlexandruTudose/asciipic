package com.asciipic.crawl.dtos;

import java.io.Serializable;
import java.util.Date;


public class ImageDTO implements Serializable {
    String name;
    long width;
    long height;
    boolean required;
    String tags;
    Date postDate;
    String size;

    public ImageDTO() {
    }

    public ImageDTO(String name, long width, long height, boolean required) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
