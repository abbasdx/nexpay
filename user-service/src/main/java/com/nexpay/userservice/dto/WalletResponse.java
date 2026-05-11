package com.nexpay.userservice.dto;

import lombok.Data;

@Data
public class WalletResponse {
    private Long id;
    private Long userId;
    private String currency;
    private Long balance;
    private Long availableBalance;
}
