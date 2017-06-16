package com.asciipic.journalize.transformers;

import com.asciipic.journalize.dtos.JournalizeCrawlDTO;
import com.asciipic.journalize.models.Job;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeCrawl;
import com.asciipic.journalize.repositories.JobRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JournalizeCrawlTransformer {

    public JournalizeCrawlDTO toDTO(JournalizeCrawl journalizeCrawl, Job job){
        JournalizeCrawlDTO journalizeCrawlDTO = new JournalizeCrawlDTO();

        UserTransformer userTransformer = new UserTransformer();
        JobTransformer jobTransformer = new JobTransformer();

        journalizeCrawlDTO.setActionType(journalizeCrawl.getJournalize().getAction());
        journalizeCrawlDTO.setActionDate(journalizeCrawl.getJournalize().getActionDate());
        journalizeCrawlDTO.setUserDetails(userTransformer.toDTO(journalizeCrawl.getUser()));
        journalizeCrawlDTO.setJobDetails(jobTransformer.toDTO(job));

        return journalizeCrawlDTO;
    }
}
