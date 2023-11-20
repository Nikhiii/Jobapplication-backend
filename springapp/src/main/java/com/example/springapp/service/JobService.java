package com.example.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.exception.ResourceNotFoundException;
import com.example.springapp.model.Job;
import com.example.springapp.repository.JobRepository;

@Service
public class JobService {
 @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

   public Job updateJob(Long id, Job jobDetails) {
    Job job = jobRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

    // update the fields of the job
    job.setJobTitle(jobDetails.getJobTitle());
    job.setDepartment(jobDetails.getDepartment());
    job.setLocation(jobDetails.getLocation());
    job.setResponsibility(jobDetails.getResponsibility());
    job.setQualification(jobDetails.getQualification());
    job.setDeadline(jobDetails.getDeadline());
    job.setCategory(jobDetails.getCategory());
    job.setAmount(jobDetails.getAmount());

    return jobRepository.save(job);
    }

    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }

    public Job getJobByJobTitle(String jobTitle) {
        return jobRepository.findByJobTitle(jobTitle);
    }

    public List<Job> getFreeJobs() {
        return jobRepository.findByCategory("free");
    }

    public List<Job> getPremiumJobs() {
        return jobRepository.findByCategory("premium");
    }
    
}
