package com.nexpay.transactionservice.service;

import com.nexpay.transactionservice.entity.Transaction;
import com.nexpay.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(Transaction request) {
        System.out.println("Creating transaction...");

        Long senderId = request.getSenderId();
        Long receiverId = request.getReceiverId();
        Double amount = request.getAmount();

        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        System.out.println("Transaction before save:"+transaction);

        Transaction saved = transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully: "+saved);

        return saved;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
