package com.example.springapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.exception.ResourceNotFoundException;
import com.example.springapp.model.Application;
import com.example.springapp.model.Job;
import com.example.springapp.model.Payment;
import com.example.springapp.repository.ApplicationRepository;
import com.example.springapp.repository.JobRepository;
import com.example.springapp.repository.PaymentRepository;


@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    public Application createApplication(Application application) {
        //  Long applicationCount = applicationRepository.countApplicationsByUserAndDate(application.getApplicationName(), LocalDate.now());
        //     if (applicationCount >= 5) {
        //         throw new TooManyApplicationsException("You have reached the maximum number of applications for today.");
        //     }
        // if (application.getJob() != null && application.getJob().getJobID() != null) {
        //     Job job = jobRepository.findById(application.getJob().getJobID())
        //             .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        //     application.setJob(job);
        // }

        // Payment payment = application.getPayment();

        // if (payment != null && payment.getUserId() == null) {
        //     payment = paymentRepository.save(payment);
        //     application.setPayment(payment);
        // }
            return applicationRepository.save(application);
        }

    public void updateApplicationStatus(Long applicationId, String status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        application.setStatus(status);
        applicationRepository.save(application);
    }

    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public void updateApplication(Long id, String status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        application.setStatus(status);
        applicationRepository.save(application);
    }

    @Transactional
    public Payment makePayment(Payment payment) {
    // Assuming payment.getApplication() returns the associated Application
    Application application = payment.getApplication();

    // Save the Application if it's a new instance
    if (application != null && application.getApplicationID() == null) {
        applicationRepository.save(application);
    }

    // Set the Application in the Payment
    payment.setApplication(application);

    // Save the Payment
    paymentRepository.save(payment);

    return payment;
}
 
    public Optional<Payment> getPaymentdetails(Long paymentId)
    {
        return paymentRepository.findById(paymentId);
   
 
    }
}
