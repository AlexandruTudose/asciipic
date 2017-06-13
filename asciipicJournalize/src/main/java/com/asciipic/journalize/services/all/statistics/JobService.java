package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.models.Job;
import jdk.nashorn.internal.scripts.JO;

public interface JobService extends GetAllService<Job>{
    Job findById(Long id);

    Job addJob(Job job);
}
