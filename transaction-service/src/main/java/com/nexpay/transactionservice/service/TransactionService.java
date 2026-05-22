package com.nexpay.transactionservice.service;

import com.nexpay.transactionservice.dto.TransactionResponse;
import com.nexpay.transactionservice.entity.Transaction;
import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(Transaction transaction);
    public Transaction getTransactionById(Long id);
    public List<Transaction> getTransactionsByUser(Long userId);
}
