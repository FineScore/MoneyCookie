package com.finescore.moneycookie.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseMessage<T> {
    @NonNull
    private String status;
    private List<T> contents;
}
