package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.dtos.postDto.JournalizeRegisterPostDTO;
import com.asciipic.journalize.models.Journalize;
import com.asciipic.journalize.models.JournalizeRegister;
import com.asciipic.journalize.repositories.JournalizeRegisterRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import com.asciipic.journalize.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private JournalizeRegisterRepository journalizeRegisterRepository;

    @Autowired
    private JournalizeRepository journalizeRepository;


    @Autowired
    private UserRepository userRepository;

    @Override
    public List<JournalizeRegister> getAll() {
        return journalizeRegisterRepository.findAll();
    }

    @Override
    public JournalizeRegister addJournalizeRegister(JournalizeRegisterPostDTO journalizeRegisterPostDTO) {

        Journalize journalize = new Journalize();
        journalize.setActionDate(new Date());
        journalize.setAction("register");
        Journalize newJournalize = journalizeRepository.save(journalize);
        JournalizeRegister journalizeRegister = new JournalizeRegister();
        journalizeRegister.setJournalize(newJournalize);
        journalizeRegister.setIp(journalizeRegisterPostDTO.getIp());
        journalizeRegister.setUser(userRepository.findOne(journalizeRegisterPostDTO.getUserId()));

        return journalizeRegisterRepository.save(journalizeRegister);
    }
}
