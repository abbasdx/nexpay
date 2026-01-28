package com.nexpay.transactionservice.repository;

import com.nexpay.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
