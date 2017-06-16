package com.asciipic.journalize.services.all.statistics;

import com.asciipic.journalize.models.Job;
import com.asciipic.journalize.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService{
    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job findById(Long id) {
        return jobRepository.findOne(id);
    }

    @Override
    public Job addJob(Job job) {
        return jobRepository.save(job);
    }
}
