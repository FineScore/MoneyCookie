package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.HoldingInfo;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.MemberInfo;
import com.finescore.moneycookie.models.SectionInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Sql("classpath:reset.sql")
class SectionRepositoryTest {

    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;

    MemberInfo saveMemberInfo;

    @BeforeEach
    void beforeEach() {
        MemberInfo info = new MemberInfo("aa@google.com", "abc", "nick", LocalDateTime.now());
        saveMemberInfo = memberRepository.save(info);
    }

    @Test
    @DisplayName("섹션 저장")
    void saveSection() {
        SectionInfo info = new SectionInfo(saveMemberInfo.getId(), 1, "제목", LocalDateTime.now());
        sectionRepository.saveSection(info);

        assertThat(info.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("보유 종목 저장")
    void saveHolding() {
        SectionInfo sectionInfo = new SectionInfo(saveMemberInfo.getId(), 1, "제목", LocalDateTime.now());
        List<ItemInfo> itemInfo = List.of(new ItemInfo("010101", "종목1", "KOSPI"));

        SectionInfo saveSection = sectionRepository.saveSection(sectionInfo);
        List<ItemInfo> saveItemInfoList = itemRepository.save(itemInfo);

        HoldingInfo holdingInfo = new HoldingInfo(saveSection.getId(), saveItemInfoList.get(0).getId(), 1, 10000, LocalDate.now());
        sectionRepository.saveHolding(holdingInfo);

        assertThat(holdingInfo.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("섹션 조회")
    void findSectionById() {
        SectionInfo sectionInfo = new SectionInfo(saveMemberInfo.getId(), 1, "제목", LocalDateTime.now());

        SectionInfo saveSection = sectionRepository.saveSection(sectionInfo);
        List<SectionInfo> info = sectionRepository.findSectionByMemberId(saveMemberInfo.getId());

        assertThat(info.get(0).getId()).isEqualTo(saveSection.getId());
        assertThat(info.get(0).getMemberId()).isEqualTo(saveSection.getMemberId());
        assertThat(info.get(0).getSectionNum()).isEqualTo(saveSection.getSectionNum());
        assertThat(info.get(0).getTitle()).isEqualTo(saveSection.getTitle());
    }

    @Test
    @DisplayName("회원별 전체 섹션 조회")
    void findSectionByMemberId() {
        SectionInfo sectionInfo1 = new SectionInfo(saveMemberInfo.getId(), 1, "제목1", LocalDateTime.now());
        SectionInfo sectionInfo2 = new SectionInfo(saveMemberInfo.getId(), 2, "제목2", LocalDateTime.now());

        SectionInfo saveSectionInfo1 = sectionRepository.saveSection(sectionInfo1);
        SectionInfo saveSectionInfo2 = sectionRepository.saveSection(sectionInfo2);
        List<SectionInfo> sectionList = sectionRepository.findSectionByMemberId(saveMemberInfo.getId());

        assertThat(sectionList.get(0)).usingRecursiveComparison().ignoringFields("createDate").isEqualTo(saveSectionInfo1);
        assertThat(sectionList.get(1)).usingRecursiveComparison().ignoringFields("createDate").isEqualTo(saveSectionInfo2);
    }

    @Test
    @DisplayName("섹션별 보유 종목 수정")
    void updateHolding() {
        SectionInfo sectionInfo1 = new SectionInfo(saveMemberInfo.getId(), 1, "제목1", LocalDateTime.now());
        List<ItemInfo> itemInfo = List.of(new ItemInfo("010101", "종목1", "KOSPI"), new ItemInfo("010102", "종목2", "KOSPI"));
        SectionInfo saveSectionInfo = sectionRepository.saveSection(sectionInfo1);
        List<ItemInfo> saveItemInfoList = itemRepository.save(itemInfo);
        HoldingInfo holdingInfo1 = new HoldingInfo(saveSectionInfo.getId(), saveItemInfoList.get(0).getId(), 1, 10000, LocalDate.now());
        HoldingInfo holdingInfo2 = new HoldingInfo(saveSectionInfo.getId(), saveItemInfoList.get(0).getId(), 1, 20000, LocalDate.now());
        HoldingInfo saveHoldingInfo1 = sectionRepository.saveHolding(holdingInfo1);
        HoldingInfo saveHoldingInfo2 = sectionRepository.saveHolding(holdingInfo2);
        saveHoldingInfo1.setQuantity(2);
        saveHoldingInfo2.setBuyPrice(30000);
        sectionRepository.updateHolding(saveSectionInfo.getId(), List.of(saveHoldingInfo1, saveHoldingInfo2));
        Optional<List<HoldingInfo>> saveHoldingsList = sectionRepository.findHoldingsById(saveSectionInfo.getId());
        assertThat(saveHoldingsList.get().get(0).getQuantity()).isEqualTo(2);
        assertThat(saveHoldingsList.get().get(1).getBuyPrice()).isEqualTo(30000);
    }
}