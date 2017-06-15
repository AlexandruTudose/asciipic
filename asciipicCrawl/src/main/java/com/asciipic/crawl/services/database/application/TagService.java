package com.asciipic.crawl.services.database.application;

import com.asciipic.crawl.models.Tag;
import com.asciipic.crawl.services.database.basic.ReadService;
import com.asciipic.crawl.services.database.basic.UpdateService;


public interface TagService extends ReadService<Tag>, UpdateService<Tag> {
    Tag getByName(String name);
}
