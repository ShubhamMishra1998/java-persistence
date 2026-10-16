package com.levelup.jpaadvancedpersistencelab.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Version
    private Long version;

    protected Account() {
    }

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getVersion() {
        return version;
    }

    public void updateBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
