package com.finescore.moneycookie.controllers;

public class FailedReason {
    private String field;
    private String message;

    public FailedReason(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
