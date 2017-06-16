package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeLoginPostDTO;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeLogin;

public interface LoginService extends GetAllService<JournalizeLogin>{

    JournalizeLogin addJournalizeLogin(JournalizeLoginPostDTO journalizeLoginPostDTO);
}
