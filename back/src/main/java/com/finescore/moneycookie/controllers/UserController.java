package com.finescore.moneycookie.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finescore.moneycookie.models.MemberInfo;
import com.finescore.moneycookie.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberInfo member, HttpServletRequest request) {
        MemberInfo memberInfo = memberService.checkInfo(member);
        log.info(member.getUsername());
        HttpSession session = request.getSession();
        session.setAttribute("username", memberInfo.getUsername());

        return new ResponseEntity<>("로그인 완료", HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberInfo member) {
        log.info("회원가입 시작");
        log.info("아이디: {}, 비밀번호: {}", member.getUsername(), member.getPassword());

        memberService.save(member);

        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> body, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        memberService.updatePassword(username, body.get("password"));

        return new ResponseEntity<>("비밀번호 변경 완료", HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        session.invalidate();

        memberService.delete(username);

        return new ResponseEntity<>("회원 탈퇴 완료", HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<String> check(String username) {
        Boolean aBoolean = memberService.checkUsername(username);

        if (!aBoolean) {
            return new ResponseEntity<>("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);
        } else {
            throw new RuntimeException();
        }
    }
}
