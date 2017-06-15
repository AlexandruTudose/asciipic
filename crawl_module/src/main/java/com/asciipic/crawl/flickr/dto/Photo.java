package com.asciipic.crawl.flickr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo implements Serializable {
    private String url_o;
    private long height_o;
    private long width_o;
    private String url_l;
    private long height_l;
    private long width_l;
    private String url_z;
    private long height_z;
    private long width_z;
    private String url_s;
    private long height_s;
    private long width_s;
    private String tags;
    private String dateupload;


    public Photo() {
        url_o = null;
        height_o = 0;
        width_o = 0;
        url_l = null;
        height_l = 0;
        width_l = 0;
        url_z = null;
        height_z = 0;
        width_z = 0;
        url_s = null;
        height_s = 0;
        width_s = 0;
    }

    public String getUrl_o() {
        return url_o;
    }

    public void setUrl_o(String url_o) {
        this.url_o = url_o;
    }

    public long getHeight_o() {
        return height_o;
    }

    public void setHeight_o(long height_o) {
        this.height_o = height_o;
    }

    public long getWidth_o() {
        return width_o;
    }

    public void setWidth_o(long width_o) {
        this.width_o = width_o;
    }

    public String getUrl_l() {
        return url_l;
    }

    public void setUrl_l(String url_l) {
        this.url_l = url_l;
    }

    public long getHeight_l() {
        return height_l;
    }

    public void setHeight_l(long height_l) {
        this.height_l = height_l;
    }

    public long getWidth_l() {
        return width_l;
    }

    public void setWidth_l(long width_l) {
        this.width_l = width_l;
    }

    public String getUrl_z() {
        return url_z;
    }

    public void setUrl_z(String url_z) {
        this.url_z = url_z;
    }

    public long getHeight_z() {
        return height_z;
    }

    public void setHeight_z(long height_z) {
        this.height_z = height_z;
    }

    public long getWidth_z() {
        return width_z;
    }

    public void setWidth_z(long width_z) {
        this.width_z = width_z;
    }

    public String getUrl_s() {
        return url_s;
    }

    public void setUrl_s(String url_s) {
        this.url_s = url_s;
    }

    public long getHeight_s() {
        return height_s;
    }

    public void setHeight_s(long height_s) {
        this.height_s = height_s;
    }

    public long getWidth_s() {
        return width_s;
    }

    public void setWidth_s(long width_s) {
        this.width_s = width_s;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDateupload() {
        return dateupload;
    }

    public void setDateupload(String dateupload) {
        this.dateupload = dateupload;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "url_o='" + url_o + '\'' +
                ", height_o=" + height_o +
                ", width_o=" + width_o +
                ", url_l='" + url_l + '\'' +
                ", height_l=" + height_l +
                ", width_l=" + width_l +
                ", url_z='" + url_z + '\'' +
                ", height_z=" + height_z +
                ", width_z=" + width_z +
                ", url_s='" + url_s + '\'' +
                ", height_s=" + height_s +
                ", width_s=" + width_s +
                ", tags='" + tags + '\'' +
                ", dateupload=" + dateupload +
                '}';
    }
}