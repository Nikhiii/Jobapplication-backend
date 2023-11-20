package com.example.springapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationID;
    private String applicationName;
    private String contactNumber;
    private String mailID;
    private String jobTitle;
    private String status;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference
    private Job job;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private Payment payment;


    public Long getApplicationID() {
        return applicationID;
    }
    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getMailID() {
        return mailID;
    }
    public void setMailID(String mailID) {
        this.mailID = mailID;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getJob() {
        return job != null ? job.getJobID() : null;
    }
    public void setJob(Job job) {
        this.job = job;
    }

    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    public Application(Long applicationID, String applicationName, String contactNumber, String mailID, String jobTitle, String status) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.contactNumber = contactNumber;
        this.mailID = mailID;
        this.jobTitle = jobTitle;
        this.status = status;
    }

    public Application() {
    }

    
    
}
