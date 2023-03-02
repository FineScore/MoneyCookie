package com.finescore.moneycookie.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@JsonIgnoreProperties({"sequence"})
public class InsertSection {
    @NotBlank(message = "제목이 입력되지 않았습니다.")
    private String title;
    private TotalRating totalRating;
    private List<InsertHolding> insertHoldingList;
}
