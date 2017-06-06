package com.asciipic.search.services;

import com.asciipic.search.dtos.ImageGetDTO;

import java.util.List;

public interface BuildResponseService {
    List<String> getResponse(ImageGetDTO imageGetDTO) throws Exception;
}
