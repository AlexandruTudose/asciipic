package com.asciipic.crawl.transformers;

import com.asciipic.crawl.dtos.ResponseDTO;
import com.asciipic.crawl.models.Crawl;
import com.asciipic.crawl.models.Url;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Component
public class CrawlToResponseDTO implements Transformer<Crawl, ResponseDTO> {
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String IN_QUEUE = "The job with id %s posted on %s was not yet taken by a worker.";
    private static final String IN_PROGRESS = "The job with id %s was taken by a worker at %s.";
    private static final String IS_DONE = "The job with id %s was finished by a worker at %s.";

    @Override
    public ResponseDTO transform(Crawl entity) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (entity.isDone()) {
            List<String> content = new ArrayList<String>();
            content.add(String.format(IS_DONE, entity.getId(), dateFormat.format((entity.getJob().getFinishDate()))));
            responseDTO.setContent(content);
            List<String> urlNames = new ArrayList<>();
            for (Url url : entity.getUrls()) {
                urlNames.add(url.getName());
            }
            responseDTO.setContent(urlNames);
        } else {
            responseDTO.setContent(null);
            if (entity.getJob().getStartDate() == null) {
                List<String> content = new ArrayList<String>();
                content.add(String.format(IN_QUEUE, entity.getId(), dateFormat.format((entity.getJob().getPostDate()))));
                responseDTO.setContent(content);
            } else {
                List<String> content = new ArrayList<String>();
                content.add(String.format(String.format(IN_PROGRESS, entity.getId(), dateFormat.format((entity.getJob().getStartDate())))));
                responseDTO.setContent(content);
            }
        }
        return responseDTO;
    }
}
