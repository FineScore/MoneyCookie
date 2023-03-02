package com.finescore.moneycookie.controllers;

import java.util.List;

public class FailedValidateResponse extends MessageResponse {
    private List<FailedReason> reason;

    public FailedValidateResponse(String status, String message) {
        super(status, message);
    }

    public FailedValidateResponse(String status, String message, List<FailedReason> reason) {
        super(status, message);
        this.reason = reason;
    }
}
