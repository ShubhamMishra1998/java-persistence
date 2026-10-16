package com.levelup.jpaadvancedpersistencelab.account;

import com.levelup.jpaadvancedpersistencelab.entity.Account;
import com.levelup.jpaadvancedpersistencelab.repository.AccountRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldCreateAccountWithVersion() {
        Account account = accountRepository.save(new Account(new BigDecimal("1000")));
        assertThat(account.getId()).isNotNull();
        assertThat(account.getVersion()).isNotNull();
        assertThat(account.getVersion()).isEqualTo(0L);
    }


}
