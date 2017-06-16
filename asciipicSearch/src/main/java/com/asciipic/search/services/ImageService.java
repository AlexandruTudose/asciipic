package com.asciipic.search.services;

import com.asciipic.search.dtos.ImagePostDTO;
import com.asciipic.search.models.Image;
import com.asciipic.search.models.SavedImage;

import java.sql.SQLException;

public interface ImageService {
    String getAsciiData(long id);

    Image addNewImage(ImagePostDTO imagePostDTO) throws SQLException;

    SavedImage findSavedImageById(Long imageId);

    Image findImageById(Long imageId);
}
