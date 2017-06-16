package com.asciipic.journalize.dtos.postDto;

public class JournalizeFilterPostDTO {
    private long userId;
    private long imageId;
    private String type;

    public JournalizeFilterPostDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
