package com.nexpay.walletservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_holds")
@Getter
@Setter
public class WalletHold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column(nullable = false)
    private String holdReference;  // unique ID for each hold

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, CAPTURED, RELEASED

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiresAt;

}

