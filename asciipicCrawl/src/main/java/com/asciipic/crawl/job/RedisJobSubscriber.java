package com.asciipic.crawl.job;


import com.asciipic.crawl.dtos.*;
import com.asciipic.crawl.flickr.FlickrAPI;
import com.asciipic.crawl.services.external.CrawlsSaver;
import com.asciipic.crawl.services.external.ImageRetriever;
import com.asciipic.crawl.services.external.ImageRetrieverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RedisJobSubscriber implements MessageListener {

    @Autowired
    private ImageRetriever imageRetriever;

    @Autowired
    private CrawlsSaver crawlsSaver;

    @Value("${server.port}")
    private String port;

    @Value("${asciipic.image-post-link}")
    private String imagePostLink;

    public void onMessage(final Message message, final byte[] pattern) {
        JobPostDTO job = new Jackson2JsonRedisSerializer<>(JobPostDTO.class).deserialize(message.getBody());
        System.out.println("xxx");
        System.out.println(job.toString());
        System.out.println("xxx");
        String url = "http://localhost:" + "9993" + "/crawls/" + job.getCrawlId();
        List<String> responseLinks = new ArrayList<>();
        CrawlUpdateDTO crawlUpdateDTO = new CrawlUpdateDTO(responseLinks);
        // Inform that the job has been taken by a worker

        new RestTemplate().postForObject(url, crawlUpdateDTO, String.class);
        System.out.println("Entered in job processor and processing crawl number " + job.getCrawlId() + ".");

        List<ImageDTO> imageDTOs = new FlickrAPI(job).getResults();

        ImagePostDTO imagePostDTO = new ImagePostDTO(job.getSite(), new Date());
        for (ImageDTO imageDTO : imageDTOs) {
            imagePostDTO.setTags(Arrays.asList(imageDTO.getTags().split(" ")));
            imagePostDTO.setPostDate(imageDTO.getPostDate());
            imagePostDTO.setSize(imageDTO.getSize());
            imagePostDTO.setUrl(imageDTO.getName());
            imagePostDTO.setWidth((int) imageDTO.getWidth());
            imagePostDTO.setHeight((int) imageDTO.getHeight());
            imagePostDTO.setByteImage(new ImageRetrieverImpl().getImage(imageDTO.getName()));

            System.out.println(imagePostDTO.toString());

            ResponseDTO responseDTO;
            try{
                responseDTO = new RestTemplate().postForObject("http://localhost:9992/images", imagePostDTO, ResponseDTO.class);
            }
            catch (Exception e){
                responseDTO = null;
            }

            if (imageDTO.isRequired()) {
                if (responseDTO != null && responseDTO.getMetadata().isSuccessful()) {
                    responseLinks.add(responseDTO.getContent().get(0));
                } else {
                    System.out.println("Request failed!");
                }
            }
        }
        crawlUpdateDTO.setLinkNames(responseLinks);
        new RestTemplate().postForObject(url, crawlUpdateDTO, String.class);
        System.out.println("Crawl number " + job.getCrawlId() + " was successfully completed.");
        System.out.println(crawlUpdateDTO.toString());
    }
}