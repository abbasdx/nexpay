package com.nexpay.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HoldResponse {

    private String holdReference;
    private Long amount;
    private String status;
    
}
