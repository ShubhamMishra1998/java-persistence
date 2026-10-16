package com.levelup.jpaadvancedpersistencelab.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    protected Order() {
    }

    public Order(String product, Customer customer) {
        this.product = product;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public Customer getCustomer() {
        return customer;
    }
}
