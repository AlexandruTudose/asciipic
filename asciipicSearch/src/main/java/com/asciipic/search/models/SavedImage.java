package com.asciipic.search.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

@Entity
@Table(name = "saved_images")
public class SavedImage {
    @Id
    @Column(name = "image_id")
    private long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false, unique = true)
    private Image image;


    @NotNull
    @Lob
    @Column(name = "data")
    private Blob data;

    @Column(name = "ascii_data", length = 10000)
    private String asciiData;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
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
