package com.example.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{

    Job findByJobTitle(String jobTitle);

    List<Job> findByCategory(String category);
    
}
