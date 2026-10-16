package com.levelup.jpaadvancedpersistencelab.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CARD")
public class CardPayment extends Payment {
    private String cardLastFour;

    protected CardPayment() {
    }

    public CardPayment(BigDecimal amount, String cardLastFour) {
        super(amount);
        this.cardLastFour = cardLastFour;
    }

    public String getCardLastFour() {
        return cardLastFour;
    }
}
