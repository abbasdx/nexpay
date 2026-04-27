package com.nexpay.walletservice.repository;

import com.nexpay.walletservice.entity.Wallet;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserId(Long userId);
    Optional<Wallet> findByUserIdAndCurrency(Long userId, String currency);

}
