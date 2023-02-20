//package com.finescore.moneycookie.repository;
//
//import com.finescore.moneycookie.models.MemberInfo;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@Transactional
//@SpringBootTest
//@Sql("classpath:reset.sql")
//class MemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @DisplayName("회원 정보 저장")
//    void saveMember() {
//        MemberInfo info = new MemberInfo("aa@google.com", "abc", "nick", LocalDateTime.now());
//
//        memberRepository.save(info);
//
//        assertThat(info.getId()).isEqualTo(1L);
//    }
//
//    @Test
//    @DisplayName("회원 정보 조회")
//    void findByEmail() {
//        MemberInfo info = new MemberInfo("aa@google.com", "abc", "nick", LocalDateTime.now());
//
//        memberRepository.save(info);
//        Optional<MemberInfo> findInfo = memberRepository.findByEmail(info.getEmail());
//
//        assertThat(info.getEmail()).isEqualTo(findInfo.get().getEmail());
//        assertThat(info.getPassword()).isEqualTo(findInfo.get().getPassword());
//        assertThat(info.getNickname()).isEqualTo(findInfo.get().getNickname());
//    }
//
//    @Test
//    @DisplayName("회원 정보 수정")
//    void updateMember() {
//        MemberInfo info = new MemberInfo("aa@google.com", "abc", "nick", LocalDateTime.now());
//
//        info = memberRepository.save(info);
//        memberRepository.update(info.getId(), "nick2");
//        Optional<MemberInfo> findInfo = memberRepository.findByEmail(info.getEmail());
//
//        assertThat(findInfo.get().getNickname()).isEqualTo("nick2");
//    }
//
//    @Test
//    @DisplayName("회원 정보 삭제")
//    void deleteMember() {
//        MemberInfo info = new MemberInfo("aa@google.com", "abc", "nick", LocalDateTime.now());
//
//        memberRepository.save(info);
//        memberRepository.delete(info.getId());
//        Optional<MemberInfo> findInfo = memberRepository.findByEmail(info.getEmail());
//
//        assertThat(findInfo).isEmpty();
//    }
//}