package com.example.springapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Application;
import com.example.springapp.model.Job;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{

    List<Application> findByJob(Job job);
    // @Query("SELECT COUNT(a) FROM Application a WHERE a.applicationName = :username AND a.dateApplied = :today")
    // Long countApplicationsByUserAndDate(@Param("username") String username, @Param("today") LocalDate today);
    
}
