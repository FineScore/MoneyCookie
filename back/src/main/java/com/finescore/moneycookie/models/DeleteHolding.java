package com.finescore.moneycookie.models;

import javax.validation.constraints.NotNull;

public class DeleteHolding {
    @NotNull(message = "보유종목 ID 값이 없습니다.")
    private Long id;
}
