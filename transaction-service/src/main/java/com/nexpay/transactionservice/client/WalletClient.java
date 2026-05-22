package com.nexpay.transactionservice.client;

import com.nexpay.transactionservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "wallet-service", url = "http://wallet-service:8085/api/wallets")
public interface WalletClient {

    @PostMapping("/debit")
    WalletResponse debit(@RequestBody DebitRequest request);

    @PostMapping("/credit")
    WalletResponse credit(@RequestBody CreditRequest request);

    @PostMapping("/hold")
    HoldResponse placeHold(@RequestBody HoldRequest request);

    @PostMapping("/capture")
    WalletResponse capture(@RequestBody CaptureRequest request);

    @PostMapping("/release/{holdReference}")
    HoldResponse release(@PathVariable String holdReference);

    @GetMapping("/{userId}")
    WalletResponse getWallet(@PathVariable Long userId);
}

