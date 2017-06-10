package com.asciipic.search.transformers;


import com.asciipic.search.dtos.ImagePostDTO;
import com.asciipic.search.models.Image;
import com.asciipic.search.models.SavedImage;
import com.asciipic.search.models.Tag;
import com.asciipic.search.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ImageTransformer {

    @Autowired
    TagRepository tagRepository;

    public ImagePostDTO toDto(Image image, SavedImage savedImage){
        ImagePostDTO imagePostDTO = new ImagePostDTO();

        List<String> stringTags = new ArrayList<>();
        List<Tag> tags = image.getTags();
        for(Tag tag: tags){
            stringTags.add(tag.getName());
        }

        imagePostDTO.setUrl(image.getUrl());
        imagePostDTO.setSource(image.getSource());
        imagePostDTO.setSize(image.getSize());
        imagePostDTO.setWidth((int) image.getWidth());
        imagePostDTO.setHeight((int) image.getHeight());
        imagePostDTO.setPostDate(image.getPostDate());
        imagePostDTO.setCrawlDate(image.getCrawlDate());
        imagePostDTO.setSaved(image.getIsSaved());
        imagePostDTO.setTags(stringTags);
        imagePostDTO.setByteImage(savedImage.getData());

        return imagePostDTO;
    }
}
