package com.levelup.jpaadvancedpersistencelab.inheritence;

import com.levelup.jpaadvancedpersistencelab.entity.CardPayment;
import com.levelup.jpaadvancedpersistencelab.entity.Payment;
import com.levelup.jpaadvancedpersistencelab.entity.UpiPayment;
import com.levelup.jpaadvancedpersistencelab.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class JpaInheritanceTest {
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void shouldPersistAndLoadDifferentPaymentTypes() {

        paymentRepository.save(new CardPayment(new BigDecimal("1000"), "1234"));

        paymentRepository.save(new UpiPayment(new BigDecimal("500"), "shubham@upi"));

        paymentRepository.flush();

        List<Payment> payments = paymentRepository.findAll();

        assertThat(payments).hasSize(2);

        assertThat(payments).anyMatch(payment -> payment instanceof CardPayment);

        assertThat(payments).anyMatch(payment -> payment instanceof UpiPayment);
    }
}
