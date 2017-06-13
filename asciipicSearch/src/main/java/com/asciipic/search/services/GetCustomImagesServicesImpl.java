package com.asciipic.search.services;

import com.asciipic.search.dtos.ImageGetDTO;
import com.asciipic.search.models.Image;
import com.asciipic.search.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GetCustomImagesServicesImpl implements GetCustomImagesService {
    @Autowired
    ImageRepository imageRepository;

    @Override
    public List<Long> getAllImages(ImageGetDTO imageGetDTO) {
        List<Long> listOfIds = new ArrayList<>();
        List<Image> images = imageRepository.findAll();
        for (Image image : images) {
            try {
                if (isAValidImage(image, imageGetDTO)) {
                    listOfIds.add(image.getId());
                }
            } catch (ParseException e) {
                return listOfIds;
            }
        }
        return listOfIds;
    }

    private boolean isAValidImage(Image image, ImageGetDTO imageGetDTO) throws ParseException {
        if (!imageGetDTO.getSource().equals("")) {
            if (!imageGetDTO.getSource().equals(image.getSource())) {
                return false;
            }
        }

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        if (!imageGetDTO.getPostDate().equals("")) {
            Date dateOne = format.parse(imageGetDTO.getPostDate());
            if (!(dateOne.getTime() <= image.getPostDate().getTime() && image.getPostDate().getTime() < dateOne.getTime() + 86399000)) {
                return false;
            }
        }

        if (!imageGetDTO.getSize().equals("")) {
            if (!imageGetDTO.getSize().equals(image.getSize())) {
                return false;
            }
        }

        int ok;
        if (!imageGetDTO.getTags().isEmpty()) {
            for (int i = 0; i < imageGetDTO.getTags().size(); i++) {
                ok = 0;
                for (int j = 0; j < image.getTags().size(); j++) {
                    if (image.getTags().get(j).getName().equals(imageGetDTO.getTags().get(i))) {
                        ok = 1;
                    }
                }
                if (ok == 0) {
                    return false;
                }
            }
        }

        return true;
    }
}
