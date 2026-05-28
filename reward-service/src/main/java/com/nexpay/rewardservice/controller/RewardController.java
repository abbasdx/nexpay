package com.nexpay.rewardservice.controller;

import com.nexpay.rewardservice.entity.Reward;
import com.nexpay.rewardservice.repository.RewardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rewards")
@CrossOrigin(origins = "http://localhost:3000")
public class RewardController {

    private final RewardRepository rewardRepository;

    RewardController(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @GetMapping
    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Reward> getRewardsByUserId(@PathVariable Long userId) {
        return rewardRepository.findByUserId(userId);
    }

    @GetMapping("/transaction/{transactionId}")
    public Reward getRewardByTransactionId(@PathVariable Long transactionId) {
        return rewardRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Reward not found for transaction ID: " + transactionId));
    }
}
