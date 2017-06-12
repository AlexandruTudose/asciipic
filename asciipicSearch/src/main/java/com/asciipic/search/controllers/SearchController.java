package com.asciipic.search.controllers;

import com.asciipic.search.dtos.ImageGetDTO;
import com.asciipic.search.dtos.ImagePostDTO;
import com.asciipic.search.dtos.ResponseDTO;
import com.asciipic.search.models.Image;
import com.asciipic.search.models.SavedImage;
import com.asciipic.search.models.Tag;
import com.asciipic.search.repositories.ImageRepository;
import com.asciipic.search.repositories.TagRepository;
import com.asciipic.search.services.BuildResponseService;
import com.asciipic.search.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    BuildResponseService buildResponseService;

    @Autowired
    ImageService imageService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;


    @RequestMapping(value = "/searches", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> getSearches(@RequestBody ImageGetDTO imageGetDTO) {
        try {
            List<String> urls = buildResponseService.getResponse(imageGetDTO);
            return new ResponseEntity<>(new ResponseDTO(true, urls), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/images/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE, method = RequestMethod.GET)
    public ResponseEntity getImageById(@PathVariable("imageId") Long imageId) throws SQLException {
        SavedImage savedImage = imageService.findImageById(imageId);

        if (savedImage == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Blob blob = savedImage.getData();
        int blobLength = (int) blob.length();
        byte[] blobAsBytes = blob.getBytes(1, blobLength);
        blob.free();

        return new ResponseEntity(blobAsBytes, HttpStatus.OK);
    }

    @RequestMapping(value = "/images/ascii/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getAsciiById(@PathVariable("imageId") Long imageId) {
        SavedImage savedImage = imageService.findImageById(imageId);
        if (savedImage == null) {
            return new ResponseEntity<>(new ResponseDTO(false, "There is no image saved with this id"), HttpStatus.NO_CONTENT);
        }
        String response = imageService.getAsciiData(imageId);

        return new ResponseEntity<>(new ResponseDTO(true, response), HttpStatus.OK);
    }

    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ResponseEntity saveImage(ImagePostDTO imagePostDTO) {
        imageService.addNewImage(imagePostDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void addImage() throws ParseException {
        Image image = new Image();
        image.setUrl("facebook");
        image.setHeight(22);
        image.setWidth(33);
        image.setSize("large");

        DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date dateOne = format.parse("21-12-2014");

        Tag newTag = new Tag();
        newTag.setName("maria");
        Tag ttt = tagRepository.save(newTag);

        List<Tag> tagssss = new ArrayList<>();
        tagssss.add(ttt);

        image.setPostDate(dateOne);
        image.setCrawlDate(dateOne);
        image.setSource("facebook");
        image.setTags(tagssss);
        image.setIsSaved(true);
        imageRepository.save(image);
    }


}