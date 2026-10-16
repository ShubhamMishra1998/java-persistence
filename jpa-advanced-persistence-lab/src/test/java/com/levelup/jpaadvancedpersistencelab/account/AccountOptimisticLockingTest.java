package com.levelup.jpaadvancedpersistencelab.account;

import com.levelup.jpaadvancedpersistencelab.entity.Account;
import com.levelup.jpaadvancedpersistencelab.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class AccountOptimisticLockingTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldDetectOptimisticLockConflict() {

        TransactionTemplate tx = new TransactionTemplate(transactionManager);

        Long accountId = tx.execute(status -> {

        Account account = accountRepository.save(new Account(new BigDecimal("1000")));

        entityManager.flush();

        return account.getId();
        });

        // Transaction A reads version 0
        Account accountA = tx.execute(status -> accountRepository.findById(accountId).orElseThrow());

        // Transaction B reads version 0
        Account accountB = tx.execute(status -> accountRepository.findById(accountId).orElseThrow());

        assertThat(accountA.getVersion()).isEqualTo(0L);
        assertThat(accountB.getVersion()).isEqualTo(0L);

        // Update using A's detached state
        tx.executeWithoutResult(status -> {

            Account current = accountRepository.findById(accountId).orElseThrow();

            current.updateBalance(new BigDecimal("1500"));

            entityManager.flush();
        });

        // Now try to update B's stale entity
        assertThatThrownBy(() ->
                tx.executeWithoutResult(status -> {

                    accountRepository.save(accountB);

                    accountB.updateBalance(new BigDecimal("2000"));

                    entityManager.flush();
                })

        ).isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }
}
