package com.nexpay.rewardservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nexpay.rewardservice.entity.Reward;
import com.nexpay.rewardservice.entity.Transaction;
import com.nexpay.rewardservice.repository.RewardRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RewardConsumer {

    private final RewardRepository rewardRepository;

    private final ObjectMapper mapper;

    public RewardConsumer(RewardRepository rewardRepository, ObjectMapper mapper) {
        this.rewardRepository = rewardRepository;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @KafkaListener(topics = "txn-initiated", groupId = "reward-group")
    public void consumerTransaction(Transaction transaction) {

        System.out.println("Received transaction: " + transaction);

        try{
            if(rewardRepository.existsByTransactionId(transaction.getId())){
                System.out.println("Reward already exists for Transaction ID: " + transaction.getId());                return;
            }
            Reward reward = new Reward();
            reward.setUserId(transaction.getSenderId());
            reward.setPoints(transaction.getAmount() * 100);
            reward.setSentAt(LocalDateTime.now());
            reward.setTransactionId(transaction.getId());

            rewardRepository.save(reward);
            System.out.println("✅ Reward created successfully for transaction ID: " + transaction.getId());
        }catch(Exception e){
            System.err.println("❌ Failed to process transaction ID: " + transaction.getId());
            e.printStackTrace();
            throw e;

        }

    }
}
