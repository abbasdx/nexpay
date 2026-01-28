package com.nexpay.transactionservice.service;

import com.nexpay.transactionservice.entity.Transaction;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();
}
