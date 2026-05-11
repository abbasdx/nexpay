package com.nexpay.userservice.client;

import com.nexpay.userservice.dto.WalletRequest;
import com.nexpay.userservice.dto.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-service", url = "http://wallet-service:8085/api/wallets")
public interface WalletClient {

    @PostMapping
    WalletResponse createWallet(@RequestBody WalletRequest walletRequest);

}
