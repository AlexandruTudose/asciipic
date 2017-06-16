package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeLogoutPostDTO;
import com.asciipic.journalize.models.JournalizeLogout;

public interface LogoutService extends GetAllService<JournalizeLogout>{
    JournalizeLogout addJournalizeLogout(JournalizeLogoutPostDTO journalizeLogoutPostDTO);
}
