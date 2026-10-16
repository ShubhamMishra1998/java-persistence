package com.levelup.jpaadvancedpersistencelab.entitygraph;

import com.levelup.jpaadvancedpersistencelab.entity.Customer;
import com.levelup.jpaadvancedpersistencelab.entity.Order;
import com.levelup.jpaadvancedpersistencelab.repository.CustomerRepository;
import com.levelup.jpaadvancedpersistencelab.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class EntityGraphTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    void shouldFetchOrdersUsingEntityGraph() {

        Customer customer = customerRepository.save(new Customer("Shubham"));

        orderRepository.save(new Order("Laptop", customer));

        orderRepository.save(new Order("Monitor", customer));

        customerRepository.flush();

        // Clear persistence context so we perform a real database fetch
        entityManager.flush();
        entityManager.clear();

        Customer loadedCustomer = customerRepository.findWithOrdersById(customer.getId()).orElseThrow();

        assertThat(loadedCustomer.getName()).isEqualTo("Shubham");

        assertThat(loadedCustomer.getOrders()).hasSize(2);
    }
}
