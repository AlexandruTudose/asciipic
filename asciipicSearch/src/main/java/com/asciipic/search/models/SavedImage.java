package com.asciipic.search.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "saved_images")
public class SavedImage {
    @Id
    @Column(name = "image_id")
    private long id;

    @NotNull
    @Lob
    @Column(name = "data", length = 100000)
    private byte[] data;

    @Column(name = "ascii_data", length = 10000)
    private String asciiData;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAsciiData() {
        return asciiData;
    }

    public void setAsciiData(String asciiData) {
        this.asciiData = asciiData;
    }
}
