package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class SectionInfo {
    private Long id;
    private Long memberId;
    private Integer sectionNum;
    private String title;
    private LocalDateTime createDate;
    private Optional<List<HoldingInfo>> holdingList;

    public SectionInfo(Long memberId, Integer sectionNum, String title, LocalDateTime createDate) {
        this.memberId = memberId;
        this.sectionNum = sectionNum;
        this.title = title;
        this.createDate = createDate;
    }

    public SectionInfo() {
    }
}
