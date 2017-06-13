package com.asciipic.journalize.transformers;

import com.asciipic.journalize.dtos.JournalizeLoginDTO;
import com.asciipic.journalize.dtos.UserDTO;
import com.asciipic.journalize.models.JournalizeLogin;

public class JournalizeLoginTransformer {

    public JournalizeLoginDTO toDTO(JournalizeLogin journalizeLogin) {
        JournalizeLoginDTO journalizeLoginDTO = new JournalizeLoginDTO();
        UserTransformer userTransformer = new UserTransformer();
        UserDTO userDTO = userTransformer.toDTO(journalizeLogin.getUser());
        journalizeLoginDTO.setActionType(journalizeLogin.getJournalize().getAction());
        journalizeLoginDTO.setActionDate(journalizeLogin.getJournalize().getActionDate());
        journalizeLoginDTO.setUserDetails(userDTO);
        journalizeLoginDTO.setIp(journalizeLogin.getIp());
        journalizeLoginDTO.setUserAgent(journalizeLogin.getUserAgent());

        return journalizeLoginDTO;
    }
}
