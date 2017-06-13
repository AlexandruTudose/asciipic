package com.asciipic.journalize.transformers;

import com.asciipic.journalize.dtos.JournalizeRegisterDTO;
import com.asciipic.journalize.models.JournalizeRegister;

public class JournalizeRegisterTransformer {

    public JournalizeRegisterDTO toDTO(JournalizeRegister journalizeRegister) {
        JournalizeRegisterDTO journalizeRegisterDTO = new JournalizeRegisterDTO();

        UserTransformer userTransformer = new UserTransformer();

        journalizeRegisterDTO.setActionType(journalizeRegister.getJournalize().getAction());
        journalizeRegisterDTO.setActionDate(journalizeRegister.getJournalize().getActionDate());
        journalizeRegisterDTO.setUserDetails(userTransformer.toDTO(journalizeRegister.getUser()));
        journalizeRegisterDTO.setIp(journalizeRegister.getIp());

        return journalizeRegisterDTO;
    }
}
