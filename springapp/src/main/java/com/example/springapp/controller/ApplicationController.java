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

import com.example.springapp.model.Application;
import com.example.springapp.model.Job;
import com.example.springapp.model.Payment;
import com.example.springapp.repository.PaymentRepository;
import com.example.springapp.service.ApplicationService;

@RestController
@RequestMapping("api/Applicant")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private PaymentRepository paymentRepo;

    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application);
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{jobId}")
    public List<Application> getApplicationsByJob(@PathVariable Long jobId) {
        Job job = new Job();
        job.setJobID(jobId);
        return applicationService.getApplicationsByJob(job);
    }
    
    // @PutMapping("/{id}")
    // public void updateApplication(@PathVariable Long id, @RequestBody Application application) {
    //     applicationService.updateApplication(id, application.getStatus());
    // }

    @PutMapping("/ChangeStatus/{id}")
    public void updateApplicationStatus(@PathVariable Long id, @RequestBody Application application) {
        applicationService.updateApplicationStatus(id, application.getStatus());
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
    }


     @PostMapping("/make-payment")
	public ResponseEntity<String> savePayment(@RequestBody Payment a){
			
		boolean s= paymentRepo.save(a)!= null ? true : false;
		if(s) {
			return new ResponseEntity<>("Payment success", HttpStatus.OK);
		}
		return new ResponseEntity<>("Payment failure", HttpStatus.NOT_FOUND);
	}

}
