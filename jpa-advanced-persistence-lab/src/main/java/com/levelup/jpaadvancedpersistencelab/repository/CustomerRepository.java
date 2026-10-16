package com.levelup.jpaadvancedpersistencelab.repository;

import com.levelup.jpaadvancedpersistencelab.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @EntityGraph(attributePaths = "orders")
    Optional<Customer> findWithOrdersById(Long id);
}
