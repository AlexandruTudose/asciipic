package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeSearchPostDTO;
import com.asciipic.journalize.models.JournalizeSearch;

public interface SearchService extends GetAllService<JournalizeSearch>{
    JournalizeSearch addJournalizeSearch(JournalizeSearchPostDTO journalizeSearchPostDTO);
}
