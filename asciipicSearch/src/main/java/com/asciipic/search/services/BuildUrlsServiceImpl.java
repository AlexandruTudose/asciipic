package com.asciipic.search.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildUrlsServiceImpl implements BuildUrlsService {

    @Value("${path.domain}")
    private String domain;

    @Value("${server.port}")
    private String port;

    public List<String> buildUrls(List<Long> ids) {

        List<String> urlsList = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            urlsList.add(domain + ":" + port + "/images/" + ids.get(i).toString());
        }
        return urlsList;
    }
}
