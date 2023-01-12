package com.finescore.moneycookie.models;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Message {
    @NonNull
    private String status;
    @NonNull
    private String sender;
    @NonNull
    private String receiver;
    private Object contents;
}
