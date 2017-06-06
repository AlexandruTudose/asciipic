package com.asciipic.search.services;

import com.asciipic.search.dtos.ImagePostDTO;
import com.asciipic.search.models.SavedImage;

public interface ImageService {
    String getAsciiData(long id);

    void addNewImage(ImagePostDTO imagePostDTO);

    SavedImage findImageById(Long imageId);
}
