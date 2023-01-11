package com.finescore.moneycookie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {
    private String status;
    private String sender;
    private String receiver;
    private String contents;
}
