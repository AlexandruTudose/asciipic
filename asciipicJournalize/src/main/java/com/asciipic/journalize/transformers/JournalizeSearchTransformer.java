package com.asciipic.journalize.transformers;

import com.asciipic.journalize.dtos.JournalizeSearchDTO;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeSearch;
import com.asciipic.journalize.repositories.JournalizeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JournalizeSearchTransformer {

    public JournalizeSearchDTO toDTO(JournalizeSearch journalizeSearch){
        JournalizeSearchDTO journalizeSearchDTO = new JournalizeSearchDTO();

        UserTransformer userTransformer = new UserTransformer();

        journalizeSearchDTO.setActionType(journalizeSearch.getJournalize().getAction());
        journalizeSearchDTO.setActionDate(journalizeSearch.getJournalize().getActionDate());
        journalizeSearchDTO.setUserDetails(userTransformer.toDTO(journalizeSearch.getUser()));
        journalizeSearchDTO.setTag(journalizeSearch.getTag());
        journalizeSearchDTO.setPostDate(journalizeSearch.getPostDate());
        journalizeSearchDTO.setHeight(journalizeSearch.getHeight());
        journalizeSearchDTO.setWidth(journalizeSearch.getWidth());

        return journalizeSearchDTO;
    }
}
