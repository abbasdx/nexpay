package com.nexpay.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletRequest {

    private Long userId;
    private String currency;

}
