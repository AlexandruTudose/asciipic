package com.asciipic.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ResponseDTO extends GenericResponseDTO<GenericMetadataDTO, List<String>> implements Serializable {
    public ResponseDTO() {
        this.setMetadata(new GenericMetadataDTO());
        this.setContent(null);
    }

    public ResponseDTO(List<String> content) {
        this.setMetadata(new GenericMetadataDTO());
        this.setContent(content);
    }

    public ResponseDTO(String stringResponse) {
        this.setMetadata(new GenericMetadataDTO());
        this.addContent(stringResponse);
    }

    public void setError(){
        this.getMetadata().setSuccessful(false);
    }

    public void addContent(String stringContent) {
        if(this.getContent() == null){
            this.setContent(new ArrayList<>());
        }
        this.getContent().add(stringContent);
    }
}
