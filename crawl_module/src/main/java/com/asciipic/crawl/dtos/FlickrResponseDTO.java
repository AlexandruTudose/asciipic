package com.asciipic.crawl.dtos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexandru on 6/11/2017.
 */
public class FlickrResponseDTO implements Serializable {
    List<ImageDTO> urls;

    public FlickrResponseDTO() {
    }

    public FlickrResponseDTO(List<ImageDTO> urls) {
        this.urls = urls;
    }

    public List<ImageDTO> getUrls() {
        return urls;
    }

    public void setUrls(List<ImageDTO> urls) {
        this.urls = urls;
    }
}
