package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeFilterPostDTO;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeFilter;
import com.asciipic.journalize.repositories.JournalizeFilterRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import com.asciipic.journalize.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    private JournalizeFilterRepository journalizeFilterRepository;

    @Autowired
    private JournalizeRepository journalizeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<JournalizeFilter> getAll() {
        return journalizeFilterRepository.findAll();
    }

    @Override
    public JournalizeFilter addJournalizeFilter(JournalizeFilterPostDTO journalizeFilterPostDTO) {
        Journalize journalize = new Journalize();
        journalize.setActionDate(new Date());
        journalize.setAction("filter");
        Journalize newJournalize = journalizeRepository.save(journalize);
        JournalizeFilter journalizeFilter = new JournalizeFilter();
        journalizeFilter.setImageId(journalizeFilterPostDTO.getImageId());
        journalizeFilter.setType(journalizeFilterPostDTO.getType());
        journalizeFilter.setUser(userRepository.findOne(journalizeFilterPostDTO.getUserId()));
        journalizeFilter.setJournalize(newJournalize);

        return journalizeFilterRepository.save(journalizeFilter);
    }
}
