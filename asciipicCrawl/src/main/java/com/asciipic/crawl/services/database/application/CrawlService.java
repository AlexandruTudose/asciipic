package com.asciipic.crawl.services.database.application;

import com.asciipic.crawl.models.Crawl;
import com.asciipic.crawl.services.database.basic.ReadService;
import com.asciipic.crawl.services.database.basic.UpdateService;


public interface CrawlService extends ReadService<Crawl>, UpdateService<Crawl> {
}
