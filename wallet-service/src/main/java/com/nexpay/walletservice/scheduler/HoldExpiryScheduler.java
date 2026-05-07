package com.nexpay.walletservice.scheduler;

import com.nexpay.walletservice.entity.WalletHold;
import com.nexpay.walletservice.repository.WalletHoldRepository;
import com.nexpay.walletservice.service.WalletService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class HoldExpiryScheduler {

    private final WalletHoldRepository walletHoldRepository;
    private final WalletService walletService;

    public HoldExpiryScheduler(WalletHoldRepository walletHoldRepository,
                               WalletService walletService) {
        this.walletHoldRepository = walletHoldRepository;
        this.walletService = walletService;
    }

    
    // Periodically scans for expired wallet holds and releases them
     
    @Scheduled(fixedRateString = "${wallet.hold.expiry.scan-rate-ms:60000}")
    public void expireOldHolds() {
        LocalDateTime now = LocalDateTime.now();

        // Retrieve all active holds that have passed their expiry time.
        List<WalletHold> expired = walletHoldRepository.findByStatusAndExpiresAtBefore("ACTIVE", now);

        for (WalletHold hold : expired) {
            String ref = hold.getHoldReference();
            try {
                // Release the hold using the existing business logic.
                walletService.releaseHold(ref);
                System.out.println("Released expired hold: " + ref);
            } catch (Exception e) {
                // Log the failure and continue processing remaining expired holds.
                System.err.println("Failed to release expired hold " + ref + ": " + e.getMessage());
            }
        }
    }
}