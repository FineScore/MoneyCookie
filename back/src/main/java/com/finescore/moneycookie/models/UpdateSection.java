package com.finescore.moneycookie.models;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class UpdateSection {
    @NotNull(message = "섹션 ID 값이 없습니다.")
    private Long id;
    private String title;
    private List<UpdateHolding> holdingList;
}
