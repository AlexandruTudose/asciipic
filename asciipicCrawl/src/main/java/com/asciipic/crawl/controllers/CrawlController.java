package com.asciipic.crawl.controllers;

import com.asciipic.crawl.dtos.CrawlPostDTO;
import com.asciipic.crawl.dtos.CrawlUpdateDTO;
import com.asciipic.crawl.dtos.ResponseDTO;
import com.asciipic.crawl.job.RedisJobPoster;
import com.asciipic.crawl.models.Crawl;
import com.asciipic.crawl.models.Job;
import com.asciipic.crawl.models.Tag;
import com.asciipic.crawl.models.Url;
import com.asciipic.crawl.services.database.application.CrawlService;
import com.asciipic.crawl.services.database.application.JobService;
import com.asciipic.crawl.services.database.application.TagService;
import com.asciipic.crawl.services.database.application.UrlService;
import com.asciipic.crawl.services.external.CrawlsSaver;
import com.asciipic.crawl.transformers.CrawlToJobPostDTO;
import com.asciipic.crawl.transformers.CrawlToResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/crawls")
public class CrawlController {
    private final java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private CrawlService crawlService;

    @Autowired
    private JobService jobService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private CrawlToResponseDTO crawlToResponseDTO;

    @Autowired
    private CrawlToJobPostDTO crawlToJobPostDTO;

    @Autowired
    private RedisJobPoster redisJobPoster;

    @Autowired
    private CrawlsSaver crawlsSaver;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ResponseDTO>> get() {
        List<ResponseDTO> crawlResponseDTOs = new ArrayList<>();
        for(Crawl crawl:crawlService.getAll()){
            crawlResponseDTOs.add(crawlToResponseDTO.transform(crawl));
        }
        return new ResponseEntity<>(crawlResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{crawl_id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getById(@PathVariable(value = "crawl_id") long id) {
        return new ResponseEntity<>(crawlToResponseDTO.transform(crawlService.getById(id)), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> postCrawl(@RequestBody CrawlPostDTO crawlPostDTO) {

        Job job = this.jobService.save(new Job());
        List<Tag> tags = getTagList(crawlPostDTO.getTags());
        Date postDate = null;
        if(crawlPostDTO.getPostDate() != null){
            try {
                postDate = simpleDateFormat.parse(crawlPostDTO.getPostDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println(crawlPostDTO.getPostDate());
        System.out.println(postDate);

        Crawl crawl = new Crawl(job, false, new ArrayList<>(), crawlPostDTO.getSource(), postDate,
                crawlPostDTO.getSize(), crawlPostDTO.getNumber(), tags);
        crawl = crawlService.save(crawl);
        redisJobPoster.post(crawlToJobPostDTO.transform(crawl));
        return new ResponseEntity<>(crawlToResponseDTO.transform(crawl), HttpStatus.OK);
    }


    @RequestMapping(value = "/{crawl_id}", method = RequestMethod.POST)
    public ResponseEntity postWorkerUpdate(@PathVariable(value = "crawl_id") Long id, @RequestBody CrawlUpdateDTO crawlUpdateDTO) {
        Job job = crawlService.getById(id).getJob();
        if (crawlUpdateDTO.getLinkNames().isEmpty() && job.getStartDate() == null) {
            job.setStartDate(new Date());
            jobService.save(job);
        } else {
            List<Url> urls = new ArrayList<>();
            for (String linkName : crawlUpdateDTO.getLinkNames()) {
                urls.add(urlService.save(new Url(linkName)));
            }
            Crawl crawl = crawlService.getById(id);
            crawl.setUrls(urls);
            crawl.setDone(true);
            crawlService.save(crawl);

            job.setFinishDate(new Date());
            jobService.save(job);

        }
        return new ResponseEntity(HttpStatus.OK);
    }


    private List<Tag> getTagList(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag tag = new Tag(tagName);
            tag = this.tagService.save(tag);
            tags.add(tag);
        }
        return tags;
    }
}
