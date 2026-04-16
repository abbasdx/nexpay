package com.nexpay.rewardservice.service;

import com.nexpay.rewardservice.entity.Reward;

import java.util.List;

public interface RewardService {
    Reward sendReward(Reward reward);
    List<Reward> getRewardsByUserId(Long userId);
}
