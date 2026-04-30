package com.nexpay.walletservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebitRequest {
    private Long userId;
    private String currency;
    private Long amount;

}
