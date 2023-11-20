package com.example.springapp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobID;
    private String jobTitle;
    private String department;
    private String location;
    private String responsibility;
    private String qualification;
    private Date deadline;
    private String category;
    private String amount;



    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Application> applications;
    
    public Long getJobID() {
        return jobID;
    }
    public void setJobID(Long jobId2) {
        this.jobID = jobId2;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getResponsibility() {
        return responsibility;
    }
    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
    public String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public List<Application> getApplications() {
        return applications;
    }
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public Job(Long jobID, String jobTitle, String department, String location, String responsibility,
            String qualification, Date deadline, String category, String amount) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.department = department;
        this.location = location;
        this.responsibility = responsibility;
        this.qualification = qualification;
        this.deadline = deadline;
        this.category = category;
        this.amount = amount;
    }

    public Job() {

    }
    

    
}
