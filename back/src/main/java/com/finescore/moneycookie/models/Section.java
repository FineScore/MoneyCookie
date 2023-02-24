package com.finescore.moneycookie.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@JsonIgnoreProperties({"sequence"})
public class Section {
    private Long id;
    private String username;
    private String title;
    private LocalDateTime createDate;
    private TotalRating totalRating;
    private List<Holding> holdingList;

    public Section(Long id, String username, String title, LocalDateTime createDate, TotalRating totalRating, List<Holding> holdingList) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.createDate = createDate;
        this.totalRating = totalRating;
        this.holdingList = holdingList;
    }

    public Section() {
    }
}
