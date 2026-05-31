package com.nexpay.rewardservice.kafka;

import com.nexpay.rewardservice.entity.Reward;
import com.nexpay.rewardservice.entity.Transaction;
import com.nexpay.rewardservice.repository.RewardRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RewardConsumer {

    private final RewardRepository rewardRepository;

    public RewardConsumer(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @KafkaListener(topics = "txn-initiated", groupId = "reward-group")
    public void consumerTransaction(Transaction transaction) {

        System.out.println("Received transaction: " + transaction);

        try{
            if(rewardRepository.existsByTransactionId(transaction.getId())){
                System.out.println("Reward already exists for Transaction ID: " + transaction.getId());                
                return;
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
