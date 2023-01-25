package com.finescore.moneycookie.models;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseMessage<T> {
    @NonNull
    private String status;
    @Nullable
    private T contents;
}
