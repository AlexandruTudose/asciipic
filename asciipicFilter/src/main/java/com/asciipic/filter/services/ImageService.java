package com.asciipic.filter.services;

import com.asciipic.filter.dtos.ImagePostDTO;

import java.io.IOException;
import java.sql.SQLException;

public interface ImageService {
    ImagePostDTO resize(ImagePostDTO imagePostDTO, Long height, Long width) throws Exception;

    ImagePostDTO crop(ImagePostDTO imagePostDTO, Long top, Long bottom, Long left, Long right) throws IOException, SQLException;
}
