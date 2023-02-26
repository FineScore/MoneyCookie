package com.finescore.moneycookie.controllers;

import lombok.Getter;

@Getter
public class DataResponse {
    private String status;
    private String message;
    private Object data;

    public DataResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public DataResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
