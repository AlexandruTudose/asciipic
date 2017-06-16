package com.asciipic.journalize.controller;

import com.asciipic.journalize.dtos.*;
import com.asciipic.journalize.dtos.postDto.*;
import com.asciipic.journalize.models.*;
import com.asciipic.journalize.repositories.ImageRepository;
import com.asciipic.journalize.repositories.JobRepository;
import com.asciipic.journalize.repositories.JournalizeRepository;
import com.asciipic.journalize.repositories.UserRepository;
import com.asciipic.journalize.services.all.statistics.*;
import com.asciipic.journalize.services.custom.statistics.*;
import com.asciipic.journalize.transformers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class Controller {

    private ComposeJsonImpl composeJson = new ComposeJsonImpl();
    private InterrogationService interrogationService = new InterrogationServiceImpl();
    private DrawServiceImp drawService = new DrawServiceImp();
    private ValidateDateServiceImpl validateDateService = new ValidateDateServiceImpl();

    @Autowired
    private JournalizeService journalizeService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private CrawlService crawlService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private JournalizeRepository journalizeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    JobService jobService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getStatistic(InformationJSON informationJSON) {
        composeJson = new ComposeJsonImpl();
        try {
            //InformationJSON informationJSON = composeJson.composeJson(requestParams);
            if (validateDateService.validate(informationJSON) == null) {
                int totalNumberOfRecords = interrogationService.getTotalNumberOfRecordingsFromCustomTable(informationJSON.getMainCommand());
                if (totalNumberOfRecords == 0) {
                    return new ResponseEntity<>("No data into in the table!", HttpStatus.BAD_REQUEST);
                }
                int recordsWithProperties = interrogationService.getTheNumberOfRecordingsWithPropertiesFromCustomTable(informationJSON);

                return new ResponseEntity<>(drawService.draw(totalNumberOfRecords, recordsWithProperties), HttpStatus.OK);
            }

            return new ResponseEntity<>(validateDateService.validate(informationJSON), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);

        }

        //to do:
        //trimitere informat ciudat a pozelor (inca nu stiu daca trnsformate in ascii sau nu)
    }

    @RequestMapping(value = "/logins", method = RequestMethod.GET)
    public ResponseEntity<List<JournalizeLoginDTO>> getAllLogin() {

        List<JournalizeLogin> journalizeLogins = loginService.getAll();
        if (journalizeLogins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        JournalizeLoginTransformer journalizeLoginTransformer = new JournalizeLoginTransformer();

        List<JournalizeLoginDTO> journalizeLoginDTOS = new ArrayList<>();
        for (JournalizeLogin journalizeLogin : journalizeLogins) {
            journalizeLoginDTOS.add(journalizeLoginTransformer.toDTO(journalizeLogin));
        }

        return new ResponseEntity<>(journalizeLoginDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/logins", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addJournalizeLogin(@RequestBody JournalizeLoginPostDTO journalizeLoginPostDTO) {
        try{
            loginService.addJournalizeLogin(journalizeLoginPostDTO);
        }catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(true, "Success!"), HttpStatus.OK);
    }

    @RequestMapping(value = "/logouts", method = RequestMethod.GET)
    public ResponseEntity<List<JournalizeLogoutDTO>> getAllLogout() {
        List<JournalizeLogout> journalizeLogouts = logoutService.getAll();
        if (journalizeLogouts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        JournalizeLogoutTransformer journalizeLogoutTransformer = new JournalizeLogoutTransformer();
        List<JournalizeLogoutDTO> journalizeLogoutDTOS = new ArrayList<>();
        for (JournalizeLogout journalizeLogout : journalizeLogouts) {
            journalizeLogoutDTOS.add(journalizeLogoutTransformer.toDTO(journalizeLogout));
        }

        return new ResponseEntity<>(journalizeLogoutDTOS, HttpStatus.OK);
    }
    @RequestMapping(value = "/logouts", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addJournalizeLogout(@RequestBody JournalizeLogoutPostDTO journalizeLoginPostDTO) {
        try{
            logoutService.addJournalizeLogout(journalizeLoginPostDTO);
        }catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(true, "Success!"), HttpStatus.OK);

    }

    @RequestMapping(value = "/registers", method = RequestMethod.GET)
    public ResponseEntity<List<JournalizeRegisterDTO>> getAllRegisters() {
        List<JournalizeRegister> journalizeRegisters = registerService.getAll();
        if (journalizeRegisters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        JournalizeRegisterTransformer journalizeRegisterTransformer = new JournalizeRegisterTransformer();
        List<JournalizeRegisterDTO> journalizeRegisterDTOS = new ArrayList<>();
        for (JournalizeRegister journalizeRegister : journalizeRegisters) {
            journalizeRegisterDTOS.add(journalizeRegisterTransformer.toDTO(journalizeRegister));
        }

        return new ResponseEntity<>(journalizeRegisterDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/registers", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addJournalizeRegister(@RequestBody JournalizeRegisterPostDTO journalizeRegisterDTOS) {

        try{
            registerService.addJournalizeRegister(journalizeRegisterDTOS);
        }catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(true,"Success!"), HttpStatus.OK);
    }

    @RequestMapping(value = "/crawls", method = RequestMethod.GET)
    public ResponseEntity<List<JournalizeCrawlDTO>> getAllCrawl() {
        List<JournalizeCrawl> journalizeCrawls = crawlService.getAll();
        if (journalizeCrawls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        JournalizeCrawlTransformer journalizeCrawlTransformer = new JournalizeCrawlTransformer();
        List<JournalizeCrawlDTO> journalizeCrawlDTOS = new ArrayList<>();
        for (JournalizeCrawl journalizeCrawl : journalizeCrawls){
            journalizeCrawlDTOS.add(journalizeCrawlTransformer.toDTO(journalizeCrawl,jobService.findById(journalizeCrawl.getJobId())));
        }
        return new ResponseEntity<>(journalizeCrawlDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/crawls", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addJournalizeCrawl(@RequestBody JournalizeCrawlPostDTO journalizeRegisterDTO) {
        try{
            crawlService.addJournalizeCrawl(journalizeRegisterDTO);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(true,"Success!"), HttpStatus.OK);
    }

    @RequestMapping(value = "/searches", method = RequestMethod.GET)
    public ResponseEntity<List<JournalizeSearchDTO>> getAllSearch() {
        List<JournalizeSearch> journalizeSearches = searchService.getAll();
        if (journalizeSearches.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        JournalizeSearchTransformer journalizeSearchTransformer = new JournalizeSearchTransformer();
        List<JournalizeSearchDTO> journalizeSearchDTOS = new ArrayList<>();
        for( JournalizeSearch journalizeSearch: journalizeSearches){
            journalizeSearchDTOS.add(journalizeSearchTransformer.toDTO(journalizeSearch));
        }

        return new ResponseEntity<>(journalizeSearchDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/searches", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addJournalizeSearch(@RequestBody JournalizeSearchPostDTO journalizeSearchePostDTO) {
        try{
            searchService.addJournalizeSearch(journalizeSearchePostDTO);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(true,"Success!"), HttpStatus.OK);
    }


    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    public ResponseEntity<List<JournalizeFilterDTO>> getAllFilters() {
        List<JournalizeFilter> journalizeFilters = filterService.getAll();
        if (journalizeFilters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        JournalizeFilterTransformer journalizeFilterTransformer = new JournalizeFilterTransformer();
        List<JournalizeFilterDTO> journalizeFilterDTOS = new ArrayList<>();
        for (JournalizeFilter journalizeFilter :journalizeFilters){
            Image image = imageRepository.findOne(journalizeFilter.getImageId());
            journalizeFilterDTOS.add(journalizeFilterTransformer.toDTO(journalizeFilter, image));
        }

        return new ResponseEntity<>(journalizeFilterDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/filters", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addJournalizeSearch(@RequestBody JournalizeFilterPostDTO journalizeFilterPostDTO) {
        try{
            filterService.addJournalizeFilter(journalizeFilterPostDTO);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false, "Error!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(true,"Success!"), HttpStatus.OK);

    }
}
