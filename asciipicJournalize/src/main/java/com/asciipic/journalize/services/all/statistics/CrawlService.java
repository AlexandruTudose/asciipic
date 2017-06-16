package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeCrawlPostDTO;
import com.asciipic.journalize.models.JournalizeCrawl;

public interface CrawlService extends GetAllService<JournalizeCrawl>{

    JournalizeCrawl addJournalizeCrawl(JournalizeCrawlPostDTO journalizeCrawlPostDTO);
}
