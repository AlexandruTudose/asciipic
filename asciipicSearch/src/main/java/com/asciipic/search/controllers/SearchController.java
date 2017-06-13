package com.asciipic.search.controllers;

import com.asciipic.search.dtos.ImageGetDTO;
import com.asciipic.search.dtos.ImagePostDTO;
import com.asciipic.search.dtos.ResponseDTO;
import com.asciipic.search.models.Image;
import com.asciipic.search.models.SavedImage;
import com.asciipic.search.repositories.ImageRepository;
import com.asciipic.search.repositories.TagRepository;
import com.asciipic.search.services.BuildResponseService;
import com.asciipic.search.services.ImageService;
import com.asciipic.search.transformers.ImageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    BuildResponseService buildResponseService;

    @Autowired
    ImageService imageService;

    @Value("${path.domain}")
    private String domain;

    @Value("${server.port}")
    private String port;


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
    public ResponseEntity<byte[]> getImageById(@PathVariable("imageId") Long imageId) throws SQLException {
        SavedImage savedImage = imageService.findSavedImageById(imageId);

        if (savedImage == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(savedImage.getData(), HttpStatus.OK);
    }

    @RequestMapping(value = "/images/metadata/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<ImagePostDTO> getImageDTOById(@PathVariable("imageId") Long imageId) throws SQLException {
        Image image = imageService.findImageById(imageId);
        SavedImage savedImage = imageService.findSavedImageById(imageId);

        if (savedImage == null || image == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ImageTransformer imageTransformer = new ImageTransformer();
        ImagePostDTO imagePostDTO = imageTransformer.toDto(image, savedImage);

        return new ResponseEntity<>(imagePostDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/images/ascii/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getAsciiById(@PathVariable("imageId") Long imageId) {
        SavedImage savedImage = imageService.findSavedImageById(imageId);
        if (savedImage == null) {
            return new ResponseEntity<>(new ResponseDTO(false, "There is no image saved with this id"), HttpStatus.NO_CONTENT);
        }

        String response = imageService.getAsciiData(imageId);

        return new ResponseEntity<>(new ResponseDTO(true, response), HttpStatus.OK);
    }

    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> saveImage(@RequestBody ImagePostDTO imagePostDTO) {
        Image savedImage = null;
        try {
            savedImage = imageService.addNewImage(imagePostDTO);
        } catch (SQLException e) {
            return new ResponseEntity<>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }

        String imageUrl = domain + ":" + port + "/images/" + savedImage.getId();

        return new ResponseEntity<>(new ResponseDTO(true, imageUrl), HttpStatus.OK);
    }
}
