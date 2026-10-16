package com.levelup.jpaadvancedpersistencelab.service;

import com.levelup.jpaadvancedpersistencelab.entity.Account;
import com.levelup.jpaadvancedpersistencelab.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(BigDecimal balance) {
        return accountRepository.save(new Account(balance));
    }

    @Transactional
    public void updateBalance(Long accountId, BigDecimal balance) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.updateBalance(balance);
    }

    @Transactional
    public Account findAccountForUpdate(Long accountId) {
        return accountRepository.findForUpdate(accountId).orElseThrow();
    }
}
