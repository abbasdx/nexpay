package com.nexpay.transactionservice.controller;

import com.nexpay.transactionservice.entity.Transaction;
import com.nexpay.transactionservice.service.TransactionService;
import com.nexpay.transactionservice.service.TransactionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Transaction transaction) {
        Transaction created = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/all")
    public List<Transaction> getAll() {
        return transactionService.getAllTransactions();
    }

}
