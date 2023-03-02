package com.finescore.moneycookie.controllers;

import lombok.Getter;

@Getter
public class SuccessDataResponse extends MessageResponse {
    private Object data;

    public SuccessDataResponse(String status, String message, Object data) {
        super(status, message);
        this.data = data;
    }
}
