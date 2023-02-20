//package com.finescore.moneycookie.repository;
//
//import com.finescore.moneycookie.models.Holding;
//import com.finescore.moneycookie.models.Item;
//import com.finescore.moneycookie.models.MemberInfo;
//import com.finescore.moneycookie.models.Section;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Transactional
//@SpringBootTest
//@Sql("classpath:reset.sql")
//class SectionRepositoryTest {
//
//    @Autowired
//    SectionRepository sectionRepository;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    ListedItemRepository itemRepository;
//
//    MemberInfo saveMemberInfo;
//
//    @BeforeEach
//    void beforeEach() {
//        MemberInfo info = new MemberInfo("aa@google.com", "abc", "nick", LocalDateTime.now());
//        saveMemberInfo = memberRepository.save(info);
//    }
//
//    @Test
//    @DisplayName("섹션 저장")
//    void saveSection() {
//        Section info = new Section(saveMemberInfo.getId(), 1, "제목", LocalDateTime.now());
//        sectionRepository.saveSection(info);
//
//        assertThat(info.getId()).isEqualTo(1L);
//    }
//
//    @Test
//    @DisplayName("보유 종목 저장")
//    void saveHolding() {
//        Section sectionInfo = new Section(saveMemberInfo.getId(), 1, "제목", LocalDateTime.now());
//        List<Item> itemInfo = List.of(new Item("010101", "종목1", "KOSPI"));
//
//        Section saveSection = sectionRepository.saveSection(sectionInfo);
//        List<Item> saveItemInfoList = itemRepository.save(itemInfo);
//
//        Holding holdingInfo = new Holding(saveSection.getId(), saveItemInfoList.get(0).getId(), 1, 10000, LocalDate.now());
//        sectionRepository.saveHolding(holdingInfo);
//
//        assertThat(holdingInfo.getId()).isEqualTo(1L);
//    }
//
//    @Test
//    @DisplayName("섹션 조회")
//    void findSectionById() {
//        Section sectionInfo = new Section(saveMemberInfo.getId(), 1, "제목", LocalDateTime.now());
//
//        Section saveSection = sectionRepository.saveSection(sectionInfo);
//        List<Section> info = sectionRepository.findSectionByMemberId(saveMemberInfo.getId());
//
//        assertThat(info.get(0).getId()).isEqualTo(saveSection.getId());
//        assertThat(info.get(0).getMemberId()).isEqualTo(saveSection.getMemberId());
//        assertThat(info.get(0).getSectionNum()).isEqualTo(saveSection.getSectionNum());
//        assertThat(info.get(0).getTitle()).isEqualTo(saveSection.getTitle());
//    }
//
//    @Test
//    @DisplayName("회원별 전체 섹션 조회")
//    void findSectionByMemberId() {
//        Section sectionInfo1 = new Section(saveMemberInfo.getId(), 1, "제목1", LocalDateTime.now());
//        Section sectionInfo2 = new Section(saveMemberInfo.getId(), 2, "제목2", LocalDateTime.now());
//
//        Section saveSectionInfo1 = sectionRepository.saveSection(sectionInfo1);
//        Section saveSectionInfo2 = sectionRepository.saveSection(sectionInfo2);
//        List<Section> sectionList = sectionRepository.findSectionByMemberId(saveMemberInfo.getId());
//
//        assertThat(sectionList.get(0)).usingRecursiveComparison().ignoringFields("createDate").isEqualTo(saveSectionInfo1);
//        assertThat(sectionList.get(1)).usingRecursiveComparison().ignoringFields("createDate").isEqualTo(saveSectionInfo2);
//    }
//
//    @Test
//    @DisplayName("섹션별 보유 종목 수정")
//    void updateHolding() {
//        Section sectionInfo1 = new Section(saveMemberInfo.getId(), 1, "제목1", LocalDateTime.now());
//        List<Item> itemInfo = List.of(new Item("010101", "종목1", "KOSPI"), new Item("010102", "종목2", "KOSPI"));
//        Section saveSectionInfo = sectionRepository.saveSection(sectionInfo1);
//        List<Item> saveItemInfoList = itemRepository.save(itemInfo);
//        Holding holdingInfo1 = new Holding(saveSectionInfo.getId(), saveItemInfoList.get(0).getId(), 1, 10000, LocalDate.now());
//        Holding holdingInfo2 = new Holding(saveSectionInfo.getId(), saveItemInfoList.get(0).getId(), 1, 20000, LocalDate.now());
//        Holding saveHoldingInfo1 = sectionRepository.saveHolding(holdingInfo1);
//        Holding saveHoldingInfo2 = sectionRepository.saveHolding(holdingInfo2);
//        saveHoldingInfo1.setQuantity(2);
//        saveHoldingInfo2.setBuyPrice(30000);
//        sectionRepository.updateHolding(saveSectionInfo.getId(), List.of(saveHoldingInfo1, saveHoldingInfo2));
//        Optional<List<Holding>> saveHoldingsList = sectionRepository.findHoldingsById(saveSectionInfo.getId());
//        assertThat(saveHoldingsList.get().get(0).getQuantity()).isEqualTo(2);
//        assertThat(saveHoldingsList.get().get(1).getBuyPrice()).isEqualTo(30000);
//    }
//}