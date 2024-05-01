package com.example.app.model;

import lombok.Getter;

@Getter
public class PaymentRequest {
    private String userId;

    public PaymentRequest() {
    }

    public PaymentRequest(String userId) {
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
