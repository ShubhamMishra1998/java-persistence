package com.levelup.jpaadvancedpersistencelab.account;

import com.levelup.jpaadvancedpersistencelab.entity.Account;
import com.levelup.jpaadvancedpersistencelab.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AccountPessimisticLockingTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Transactional
    void shouldAcquirePessimisticWriteLock() {

        Account account = accountRepository.saveAndFlush(new Account(new BigDecimal("1000")));

        Account lockedAccount = accountRepository.findForUpdate(account.getId()).orElseThrow();

        assertThat(lockedAccount.getId()).isEqualTo(account.getId());

        assertThat(lockedAccount.getBalance()).isEqualByComparingTo("1000");
    }
}
