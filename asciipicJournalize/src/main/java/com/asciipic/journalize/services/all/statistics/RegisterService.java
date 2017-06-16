package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeRegisterPostDTO;
import com.asciipic.journalize.models.JournalizeRegister;

public interface RegisterService extends GetAllService<JournalizeRegister>{
    JournalizeRegister addJournalizeRegister(JournalizeRegisterPostDTO journalizeRegisterPostDTO);
}
