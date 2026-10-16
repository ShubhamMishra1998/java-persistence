package com.levelup.jpaadvancedpersistencelab.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("UPI")
public class UpiPayment extends Payment {
    private String upiId;

    protected UpiPayment() {
    }

    public UpiPayment(BigDecimal amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }

    public String getUpiId() {
        return upiId;
    }
}
