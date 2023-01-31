package com.whitebox.bankaccount.repository;

import com.whitebox.bankaccount.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findAllByAccountIdOrderByDateDesc(UUID accountId);
    List<BankTransaction> findAllByAccountIdAndDateAfterOrderByDateDesc(UUID accountId, Instant since);
}