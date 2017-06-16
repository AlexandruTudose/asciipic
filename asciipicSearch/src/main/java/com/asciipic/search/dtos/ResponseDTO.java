package com.asciipic.search.dtos;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseDTO implements Serializable {

    private Metadata metadata;
    private List<String> content;

    public ResponseDTO() {
    }

    public ResponseDTO(Boolean successful, List<String> urls) {
        this.metadata = new Metadata(successful);
        this.content = urls;
    }

    public ResponseDTO(Boolean successful, String string) {
        this.metadata = new Metadata(successful);
        this.content = new ArrayList<>();
        this.content.add(string);
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    private class Metadata implements Serializable {
        private Boolean successful;
        public Metadata(){

        }

        Metadata(Boolean successful) {
            this.successful = successful;
        }

        public Boolean getSuccessful() {
            return successful;
        }

        public void setSuccessful(Boolean successful) {
            this.successful = successful;
        }
    }


}

