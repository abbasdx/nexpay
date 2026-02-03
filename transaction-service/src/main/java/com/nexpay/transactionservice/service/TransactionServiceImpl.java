package com.nexpay.transactionservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexpay.transactionservice.entity.Transaction;
import com.nexpay.transactionservice.kafka.KafkaEventProducer;
import com.nexpay.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;
    private final KafkaEventProducer kafkaEventProducer;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ObjectMapper objectMapper, KafkaEventProducer kafkaEventProducer) {
        this.transactionRepository = transactionRepository;
        this.objectMapper = objectMapper;
        this.kafkaEventProducer = kafkaEventProducer;
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

        try{
//            String eventPayload = objectMapper.writeValueAsString(saved);
//            String key = String.valueOf(saved.getId());
//            kafkaEventProducer.sendTransactionEvent(eventPayload, key);

            String key = String.valueOf(saved.getId());
            kafkaEventProducer.sendTransactionEvent(key, saved);
            System.out.println("✅ Kafka Message sent successfully");

        }catch (Exception e){
            System.err.println("❌ Failed to sent Kafka event");
            e.printStackTrace();
        }
        return saved;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
