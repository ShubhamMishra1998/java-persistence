package com.levelup.jpaadvancedpersistencelab.repository;

import com.levelup.jpaadvancedpersistencelab.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
