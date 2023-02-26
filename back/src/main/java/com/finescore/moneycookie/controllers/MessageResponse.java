package com.finescore.moneycookie.controllers;

import lombok.Getter;

@Getter
public class MessageResponse {
    private String status;
    private String message;

    public MessageResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
