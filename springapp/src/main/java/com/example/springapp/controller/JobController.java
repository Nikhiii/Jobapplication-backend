package com.example.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Job;
import com.example.springapp.model.Payment;
import com.example.springapp.repository.PaymentRepository;
import com.example.springapp.service.JobService;

@RestController
@RequestMapping("api/Job")
public class JobController {
    
    @Autowired
    private JobService jobService;

    @Autowired
    private PaymentRepository paymentRepo;

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{jobTitle}")
    public Job getJobByJobTitle(@PathVariable String jobTitle) {
        return jobService.getJobByJobTitle(jobTitle);
    }

    @PutMapping("/{id}")
    public void updateJob(@PathVariable Long id, @RequestBody Job job) {
        jobService.updateJob(id, job);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    @GetMapping("/freeJobs")
    public List<Job> getFreeJobs() {
        return jobService.getFreeJobs();
    }

    @GetMapping("/premiumJobs")
    public List<Job> getPremiumJobs() {
        return jobService.getPremiumJobs();
    }


    @GetMapping("/getPayments")
    public ResponseEntity<List<Payment>> getPayments() {
        List<Payment> payments = paymentRepo.findAll();
        return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
    }


}
