package com.finescore.moneycookie.controllers;

public class MessageResponse {
    private String status;
    private String message;

    public MessageResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
