package com.levelup.jpaadvancedpersistencelab.transaction;

import com.levelup.jpaadvancedpersistencelab.entity.TransactionRecord;
import com.levelup.jpaadvancedpersistencelab.repository.TransactionRecordRepository;
import com.levelup.jpaadvancedpersistencelab.service.TransactionDemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class TransactionPropagationTest {
    @Autowired
    private TransactionDemoService transactionDemoService;

    @Autowired
    private TransactionRecordRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldCommitOuterTransactionButRollbackRequiresNewTransaction() {
        transactionDemoService.execute();
        List<TransactionRecord> records = repository.findAll();
        assertThat(records).hasSize(2);
        assertThat(records)
                .extracting(TransactionRecord::getName)
                .containsExactlyInAnyOrder(
                        "Record-1",
                        "Record-3"
                );
    }
}
