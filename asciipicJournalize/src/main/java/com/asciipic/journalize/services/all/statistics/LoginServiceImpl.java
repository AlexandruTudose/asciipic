package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeLoginPostDTO;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeLogin;
import com.asciipic.journalize.models.JournalizeRegister;
import com.asciipic.journalize.repositories.JournalizeLoginRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import com.asciipic.journalize.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private JournalizeLoginRepository journalizeLoginRepository;

    @Autowired
    private JournalizeRepository journalizeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<JournalizeLogin> getAll() {
        return journalizeLoginRepository.findAll();
    }


    @Override
    public JournalizeLogin addJournalizeLogin(JournalizeLoginPostDTO journalizeLoginPostDTO) {
        Journalize journalize = new Journalize();
        journalize.setActionDate(new Date());
        journalize.setAction("login");
        Journalize newJournalize = journalizeRepository.save(journalize);
        JournalizeLogin journalizeLogin = new JournalizeLogin();
        journalizeLogin.setId(journalize.getId());
        journalizeLogin.setIp(journalizeLoginPostDTO.getIp());
        journalizeLogin.setJournalize(newJournalize);
        journalizeLogin.setUser(userRepository.findOne(journalizeLoginPostDTO.getUserId()));
        journalizeLogin.setUserAgent(journalizeLoginPostDTO.getUserAgent());
        return journalizeLoginRepository.save(journalizeLogin);
    }
}
