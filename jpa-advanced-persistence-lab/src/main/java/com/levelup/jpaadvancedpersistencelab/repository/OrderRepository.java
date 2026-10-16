package com.levelup.jpaadvancedpersistencelab.repository;

import com.levelup.jpaadvancedpersistencelab.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
