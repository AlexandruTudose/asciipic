package com.asciipic.journalize.transformers;

import com.asciipic.journalize.dtos.JournalizeFilterDTO;
import com.asciipic.journalize.models.Image;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeFilter;
import com.asciipic.journalize.repositories.ImageRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JournalizeFilterTransformer {

    public JournalizeFilterDTO toDTO(JournalizeFilter journalizeFilter, Image image){
        JournalizeFilterDTO journalizeFilterDTO = new JournalizeFilterDTO();

        UserTransformer userTransformer = new UserTransformer();

        journalizeFilterDTO.setActionType(journalizeFilter.getJournalize().getAction());
        journalizeFilterDTO.setActionDate(journalizeFilter.getJournalize().getActionDate());
        journalizeFilterDTO.setUserDetails(userTransformer.toDTO(journalizeFilter.getUser()));
        journalizeFilterDTO.setImagePostDate(image.getPostDate());
        journalizeFilterDTO.setHeight(image.getHeight());
        journalizeFilterDTO.setWidth(image.getWidth());
        journalizeFilterDTO.setTypeFilter(journalizeFilter.getType());

        return journalizeFilterDTO;
    }
}
