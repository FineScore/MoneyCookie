package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateForm {
    private Long id;
    private String title;
    private List<UpdateHolding> holdingList;
}
