package com.asciipic.crawl.services.database.application;

import com.asciipic.crawl.models.Url;
import com.asciipic.crawl.services.database.basic.ReadService;
import com.asciipic.crawl.services.database.basic.UpdateService;


public interface UrlService extends ReadService<Url>, UpdateService<Url> {
    Url getByName(String name);
}
