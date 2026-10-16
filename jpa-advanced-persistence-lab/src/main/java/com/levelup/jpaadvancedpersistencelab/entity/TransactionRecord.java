package com.levelup.jpaadvancedpersistencelab.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_records")
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected TransactionRecord() {
    }

    public TransactionRecord(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
