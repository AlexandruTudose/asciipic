package com.asciipic.crawl.services.database.application;

import com.asciipic.crawl.models.Job;
import com.asciipic.crawl.services.database.basic.ReadService;
import com.asciipic.crawl.services.database.basic.UpdateService;


public interface JobService extends ReadService<Job>, UpdateService<Job> {
}
