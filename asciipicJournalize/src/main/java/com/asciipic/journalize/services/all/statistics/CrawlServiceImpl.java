package com.asciipic.journalize.services.all.statistics;

import java.util.Date;
import java.util.List;

import com.asciipic.journalize.dtos.postDto.JournalizeCrawlPostDTO;
import com.asciipic.journalize.models.Job;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeCrawl;
import com.asciipic.journalize.models.JournalizeRegister;
import com.asciipic.journalize.repositories.JournalizeCrawlRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import com.asciipic.journalize.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CrawlServiceImpl implements CrawlService{

    @Autowired
    private JournalizeCrawlRepository journalizeCrawlRepository;

    @Autowired
    private JournalizeRepository journalizeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<JournalizeCrawl> getAll() {
        return journalizeCrawlRepository.findAll();
    }

    @Override
    public JournalizeCrawl addJournalizeCrawl(JournalizeCrawlPostDTO journalizeCrawlPostDTO) {
        //verif
        Journalize journalize = new Journalize();
        journalize.setActionDate(new Date());
        journalize.setAction("crawl");
        Journalize newJournalize = journalizeRepository.save(journalize);
        JournalizeCrawl journalizeCrawl = new JournalizeCrawl();
        journalizeCrawl.setJournalize(newJournalize);
        journalizeCrawl.setSize(journalizeCrawlPostDTO.getSize());
        journalizeCrawl.setTag(journalizeCrawlPostDTO.getTag());
        journalizeCrawl.setPostDate(journalizeCrawlPostDTO.getPostDate());

        journalizeCrawl.setJobId(journalizeCrawlPostDTO.getJobId());
        journalizeCrawl.setUser(userRepository.findOne(journalizeCrawlPostDTO.getUserId()));

        return journalizeCrawlRepository.save(journalizeCrawl);
    }
}
