package com.nexpay.rewardservice.service;

import com.nexpay.rewardservice.entity.Reward;
import com.nexpay.rewardservice.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    @Override
    public Reward sendReward(Reward reward) {
        reward.setSentAt(LocalDateTime.now());
        return rewardRepository.save(reward);
    }

    @Override
    public List<Reward> getRewardsByUserId(Long userId) {
        return rewardRepository.findByUserId(userId);
    }
}
