package com.levelup.jpaadvancedpersistencelab.service;

import com.levelup.jpaadvancedpersistencelab.entity.TransactionRecord;
import com.levelup.jpaadvancedpersistencelab.repository.TransactionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndependentTransactionService {
    private final TransactionRecordRepository repository;

    public IndependentTransactionService(TransactionRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAndFail() {
        repository.save(new TransactionRecord("Record-2"));
        throw new RuntimeException("Simulated failure");
    }
}
