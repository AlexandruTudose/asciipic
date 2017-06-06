package com.asciipic.search.services;

import com.asciipic.search.dtos.ImageGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildResponseServiceImpl implements BuildResponseService {

    @Autowired
    GetCustomImagesService getCustomImagesService;

    @Autowired
    BuildUrlsService buildUrlsService;

    public List<String> getResponse(ImageGetDTO imageGetDTO) throws Exception {
        List<Long> ids = getCustomImagesService.getAllImages(imageGetDTO);
        List<String> urlList = buildUrlsService.buildUrls(ids);

        if (urlList.isEmpty()) {
            throw new Exception("There is no image with this properties!");
        }
        return urlList;
    }
}
