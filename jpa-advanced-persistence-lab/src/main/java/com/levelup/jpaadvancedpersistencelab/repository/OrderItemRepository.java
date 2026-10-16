package com.levelup.jpaadvancedpersistencelab.repository;

import com.levelup.jpaadvancedpersistencelab.entity.OrderItem;
import com.levelup.jpaadvancedpersistencelab.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
