package com.asciipic.crawl.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ImagePostDTO implements Serializable {

    private String url;
    private String source;
    private String size;
    private int height;
    private int width;
    private Date postDate;
    private Date crawlDate;
    private Boolean isSaved;
    private List<String> tags;
    private byte[] byteImage;

    public ImagePostDTO() {
        isSaved = true;
    }

    public ImagePostDTO(String source, Date crawlDate) {
        this.source = source;
        this.crawlDate = crawlDate;
        this.isSaved = true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getCrawlDate() {
        return crawlDate;
    }

    public void setCrawlDate(Date crawlDate) {
        this.crawlDate = crawlDate;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }

    @Override
    public String toString() {
        return "ImagePostDTO{" +
                "url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", size='" + size + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", postDate=" + postDate +
                ", crawlDate=" + crawlDate +
                ", isSaved=" + isSaved +
                ", tags=" + tags +
                '}';
    }
}