package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeFilterPostDTO;
import com.asciipic.journalize.models.JournalizeFilter;

public interface FilterService extends GetAllService<JournalizeFilter> {
    JournalizeFilter addJournalizeFilter(JournalizeFilterPostDTO journalizeFilterPostDTO);
}
