package com.asciipic.crawl.flickr;

import com.asciipic.crawl.dtos.ImageDTO;
import com.asciipic.crawl.dtos.JobPostDTO;
import com.asciipic.crawl.flickr.dto.Photo;
import com.asciipic.crawl.flickr.dto.PhotoSearchResult;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlickrAPI {
    private final String linkHeader = "https://api.flickr.com/services/rest/?method=flickr.photos.search&format=json&" +
            "nojsoncallback=1&api_key=2c584c0a6b21310581ab99a610b2e5bd&tag_mode=all&media=photos&privacy_filter=public" +
            "&extras=url_s,url_z,url_l,url_o,tags,date_upload";
    private final java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");


    private JobPostDTO job;
    private PhotoSearchResult photoSearchResult;
    private List<ImageDTO> results;
    private long size;

    public FlickrAPI(JobPostDTO job) {
        this.job = job;
        results = null;
        size = 0;
    }

    ImageDTO createImageDTO(Photo photo, String size, boolean required){
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setPostDate(new Date(Long.parseLong(photo.getDateupload()) * 1000));
        imageDTO.setTags(photo.getTags());
        imageDTO.setSize(size);
        imageDTO.setRequired(required);
        switch (size){
            case "original":{
                imageDTO.setName(photo.getUrl_o());
                imageDTO.setHeight(photo.getHeight_o());
                imageDTO.setWidth(photo.getWidth_o());
                break;
            }
            case "small":{
                imageDTO.setName(photo.getUrl_s());
                imageDTO.setHeight(photo.getHeight_s());
                imageDTO.setWidth(photo.getWidth_s());
                break;
            }
            case "medium":{
                imageDTO.setName(photo.getUrl_z());
                imageDTO.setHeight(photo.getHeight_z());
                imageDTO.setWidth(photo.getWidth_z());
                break;
            }
            case "large":{
                imageDTO.setName(photo.getUrl_l());
                imageDTO.setHeight(photo.getHeight_l());
                imageDTO.setWidth(photo.getWidth_l());
                break;
            }
        }
        return imageDTO;
    }

    public List<ImageDTO> getResults() {
        photoSearchResult = new RestTemplate().getForObject(getInterrogationLink(), PhotoSearchResult.class);

        if (photoSearchResult.getStat().matches("ok")) {
            results = new ArrayList<>();
        }
        for (Photo photo : photoSearchResult.getPhotos().getPhoto()) {
            if (size == job.getNumber()) {
                break;
            } else {

                if (job.getSize().matches("original") && photo.getUrl_o() != null) {
                    size++;
                    results.add(createImageDTO(photo, "original", true));
                }
                if (photo.getUrl_s() != null) {
                    boolean required;
                    if (job.getSize().matches("small")) {
                        required = true;
                        size++;
                    } else {
                        required = false;
                    }
                    results.add(createImageDTO(photo, "small", required));
                }
                if (photo.getUrl_z() != null) {
                    boolean required;
                    if (job.getSize().matches("medium")) {
                        required = true;
                        size++;
                    } else {
                        required = false;
                    }
                    results.add(createImageDTO(photo, "medium", required));
                }
                if (photo.getUrl_l() != null) {
                    boolean required;
                    if (job.getSize().matches("large")) {
                        required = true;
                        size++;
                    } else {
                        required = false;
                    }
                    results.add(createImageDTO(photo, "large", required));
                }
            }
        }
        // System.out.println(results);
        return results;
    }

    private String getInterrogationLink() {
        StringBuilder link = new StringBuilder(linkHeader);
        if (!job.getTags().isEmpty()) {
            link.append("&tags=");
        }
        for (String tag : job.getTags()) {
            link.append(tag).append(",");
        }
        link.delete(link.length() - 1, link.length());
        link.append("&per_page=").append(job.getNumber()*4 );
        if (job.getPostDate() != null) {
            link.append("&min_upload_date=").append((job.getPostDate().getTime()) / 1000);
            link.append("&max_upload_date=").append(job.getPostDate().getTime() / 1000 + 86399);
        }
        System.out.println(link.toString());
        return link.toString();
    }
}
