package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeSearchPostDTO;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeSearch;
import com.asciipic.journalize.repositories.JournalizeRepository;
import com.asciipic.journalize.repositories.JournalizeSearchRepository;
import com.asciipic.journalize.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private JournalizeSearchRepository journalizeSearchRepository;

    @Autowired
    private JournalizeRepository journalizeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<JournalizeSearch> getAll() {
        return journalizeSearchRepository.findAll();
    }

    @Override
    public JournalizeSearch addJournalizeSearch(JournalizeSearchPostDTO journalizeSearchPostDTO) {

        Journalize journalize = new Journalize();
        journalize.setActionDate(new Date());
        journalize.setAction("search");
        Journalize newJournalize = journalizeRepository.save(journalize);
        JournalizeSearch journalizeSearch = new JournalizeSearch();
        journalizeSearch.setJournalize(newJournalize);
        journalizeSearch.setUser(userRepository.findOne(journalizeSearchPostDTO.getUserId()));
        journalizeSearch.setPostDate(journalizeSearchPostDTO.getPostDate());
        journalizeSearch.setTag(journalizeSearchPostDTO.getTag());
        journalizeSearch.setHeight(journalizeSearchPostDTO.getHeight());
        journalizeSearch.setWidth(journalizeSearchPostDTO.getWidth());

        return journalizeSearchRepository.save(journalizeSearch);
    }
}
