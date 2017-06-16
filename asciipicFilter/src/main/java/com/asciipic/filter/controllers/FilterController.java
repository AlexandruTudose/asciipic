package com.asciipic.filter.controllers;

import com.asciipic.filter.dtos.ImagePostDTO;
import com.asciipic.filter.dtos.ResponseDTO;
import com.asciipic.filter.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FilterController {

    @Autowired
    private ImageService imageService;

    @Value("${path.domain}")
    private String domain;

    @Value("${server.port}")
    private String port;

    @Value("${search.port}")
    private String searchPort;

    @RequestMapping(value = "/filter/resize", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> resizeImage(@RequestBody ImagePostDTO imagePostDTO,
            @RequestParam(value = "height", required = false) Long height,
            @RequestParam(value = "width", required = false) Long width) {
        height = verifyParameter(height);
        width = verifyParameter(width);

        if (height < 0 || width < 0) {
            return new ResponseEntity<>(new ResponseDTO(false, "The arguments must be grater then 0"), HttpStatus.OK);
        }

        ImagePostDTO newImage;
        try {
            newImage = imageService.resize(imagePostDTO, height, width);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }

        ResponseDTO newResponse = addImage(newImage);
        return new ResponseEntity<>(newResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/filter/crop", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> cropImage(@RequestBody ImagePostDTO imagePostDTO,
            @RequestParam(value = "top", required = false) Long top,
            @RequestParam(value = "bottom", required = false) Long bottom,
            @RequestParam(value = "left", required = false) Long left,
            @RequestParam(value = "right", required = false) Long right) {
        top = verifyParameter(top);
        bottom = verifyParameter(bottom);
        left = verifyParameter(left);
        right = verifyParameter(right);

        if (top + bottom >= imagePostDTO.getHeight() || left + right >= imagePostDTO.getWidth()) {
            return new ResponseEntity<>(new ResponseDTO(false, "The arguments are too big"), HttpStatus.OK);
        }

        if (top < 0 || bottom < 0 || left < 0 || right < 0) {
            return new ResponseEntity<>(new ResponseDTO(false, "The arguments must be grater then 0"), HttpStatus.OK);
        }

        if (top == 0 && bottom == 0 && left == 0 && right == 0) {
            return new ResponseEntity<>(new ResponseDTO(false, "Must have at least one argument"), HttpStatus.OK);
        }

        ImagePostDTO newImage;
        try {
            newImage = imageService.crop(imagePostDTO, top, bottom, left, right);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }

        ResponseDTO newResponse = addImage(newImage);
        return new ResponseEntity<>(newResponse, HttpStatus.OK);
    }


    private ResponseDTO addImage(ImagePostDTO imagePostDTO) {
        final String uri = domain + ":" + searchPort + "/images";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(uri, imagePostDTO, ResponseDTO.class);
    }

    private Long verifyParameter(Long number) {
        if (number == null) {
            number = Long.valueOf(0);
        }
        return number;
    }
}
