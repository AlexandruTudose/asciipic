package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeLogoutPostDTO;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeLogout;
import com.asciipic.journalize.repositories.JournalizeLogoutRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import com.asciipic.journalize.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogoutServiceImpl implements LogoutService{

    @Autowired
    private JournalizeLogoutRepository journalizeLogoutRepository;

    @Autowired
    private JournalizeRepository journalizeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<JournalizeLogout> getAll() {
        return journalizeLogoutRepository.findAll();
    }

    @Override
    public JournalizeLogout addJournalizeLogout(JournalizeLogoutPostDTO journalizeLogoutPostDTO) {
        Journalize journalize = new Journalize();
        journalize.setActionDate(new Date());
        journalize.setAction("logout");
        Journalize newJournalize = journalizeRepository.save(journalize);
        JournalizeLogout journalizeLogout = new JournalizeLogout();
        journalizeLogout.setJournalize(newJournalize);
        journalizeLogout.setUser(userRepository.findOne(journalizeLogoutPostDTO.getUserId()));
        journalizeLogout.setCause(journalizeLogoutPostDTO.getCause());
        return journalizeLogoutRepository.save(journalizeLogout);
    }
}
