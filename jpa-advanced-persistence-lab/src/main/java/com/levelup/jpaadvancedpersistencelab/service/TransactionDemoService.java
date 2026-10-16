package com.levelup.jpaadvancedpersistencelab.service;

import com.levelup.jpaadvancedpersistencelab.entity.TransactionRecord;
import com.levelup.jpaadvancedpersistencelab.repository.TransactionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionDemoService {
    private final TransactionRecordRepository repository;
    private final IndependentTransactionService independentTransactionService;

    public TransactionDemoService(TransactionRecordRepository repository, IndependentTransactionService independentTransactionService) {
        this.repository = repository;
        this.independentTransactionService = independentTransactionService;
    }

    @Transactional
    public void execute() {

        // Transaction #1
        repository.save(new TransactionRecord("Record-1"));

        try {
            // Separate transaction
            independentTransactionService.saveAndFail();

        } catch (RuntimeException e) {
            // Intentionally continue the outer transaction
        }

        // Transaction #1 continues
        repository.save(new TransactionRecord("Record-3"));
    }
}
