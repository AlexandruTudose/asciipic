package com.asciipic.search.services;

import com.asciipic.search.dtos.ImageGetDTO;

import java.util.List;

public interface GetCustomImagesService {
    List<Long> getAllImages(ImageGetDTO informationJSON);
}

