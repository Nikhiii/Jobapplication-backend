package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.springapp.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    List<Payment> findAllByUserId(Long userId);
    
}
