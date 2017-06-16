package com.asciipic.search.services;

import com.asciipic.search.dtos.ImagePostDTO;
import com.asciipic.search.models.Image;
import com.asciipic.search.models.SavedImage;
import com.asciipic.search.models.Tag;
import com.asciipic.search.repositories.ImageRepository;
import com.asciipic.search.repositories.SavedImageRepository;
import com.asciipic.search.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    SavedImageRepository savedImageRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AsciiService asciiService;

    @Override
    public SavedImage findSavedImageById(Long imageId) {
        return savedImageRepository.findOne(imageId);
    }

    @Override
    public Image findImageById(Long imageId) {
        return imageRepository.findOne(imageId);
    }

    @Override
    public String getAsciiData(long id) {
        return savedImageRepository.findOne(id).getAsciiData();
    }

    @Override
    public Image addNewImage(ImagePostDTO imagePostDTO) throws SQLException {
//        TODO: verify the input
        imagePostDTO.getTags();

        List<String> listOfTags = imagePostDTO.getTags();
        List<Tag> newListOfTags = new ArrayList<>();
        Tag newTag;

        for (String listOfTag : listOfTags) {
            if (tagRepository.findNumberByName(listOfTag) == 0) {
                Tag tag = new Tag(listOfTag);
                newTag = tagRepository.save(tag);
            } else {
                newTag = new Tag();
                newTag.setId(tagRepository.findIdByName(listOfTag));
                newTag.setName(listOfTag);
            }
            newListOfTags.add(newTag);
        }

        Image image = new Image();
        image.setUrl(imagePostDTO.getUrl());
        image.setSource(imagePostDTO.getSource());
        image.setSize(imagePostDTO.getSize());
        image.setWidth(imagePostDTO.getWidth());
        image.setHeight(imagePostDTO.getHeight());
        image.setPostDate(imagePostDTO.getPostDate());
        image.setCrawlDate(imagePostDTO.getCrawlDate());
        image.setIsSaved(imagePostDTO.getSaved());
        image.setTags(newListOfTags);
        Image newImage = imageRepository.save(image);

        SavedImage savedImage = new SavedImage();
        byte[] bytes = new byte[2];
        Arrays.fill(bytes, (byte) 1);

        Blob imageBlob = new SerialBlob(imagePostDTO.getByteImage());
        savedImage.setId(newImage.getId());
        savedImage.setData(bytes);
        savedImage.setAsciiData(asciiService.transformImageToAscii(imageBlob));

        savedImageRepository.save(savedImage);
        savedImageRepository.updateBlob(imagePostDTO.getByteImage(), newImage.getId());

        return newImage;
    }
}
