package com.asciipic.crawl.dtos;

import java.io.Serializable;

public class GenericMetadataDTO implements Serializable {
    private boolean successful;

    public GenericMetadataDTO() {
        successful = true;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
