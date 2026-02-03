package com.nexpay.transactionservice.dto;

public class TransferRequest {

    private Long senderId;

    private Long receiverId;

    private Double amount;

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
