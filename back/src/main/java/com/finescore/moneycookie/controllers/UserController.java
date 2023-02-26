package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.User;
import com.finescore.moneycookie.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/check")
    public ResponseEntity<MessageResponse> checkDuplicateUsername(String username) {
        Optional<String> savedUsername = userService.findForDuplicateCheck(username);

        if (savedUsername.isPresent()) {
            MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "이미 존재하는 아이디");

            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        } else {
            MessageResponse messageResponse = new MessageResponse("SUCCESS", "사용 가능한 아이디");

            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody User tryUser, HttpServletRequest request) {
        log.info(tryUser.getUsername());
        Optional<User> savedUser = userService.findByUsername(tryUser.getUsername());

        if (savedUser.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("username", savedUser.get().getUsername());

            Boolean isEqualsPassword = userService.isEqualsPassword(tryUser, savedUser.get());

            if (!isEqualsPassword) {
                MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "비밀번호 불일치");

                return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
            }

            MessageResponse messageResponse = new MessageResponse("SUCCESS", "로그인 완료");

            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "존재하지 않는 회원");

            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "로그아웃 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody User user) {
        log.info("회원가입 시작");
        log.info("아이디: {}, 비밀번호: {}", user.getUsername(), user.getPassword());

        userService.save(user);

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "회원가입 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<MessageResponse> updatePassword(@RequestBody Map<String, String> body, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        userService.updatePassword(username, body.get("password"));

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "비밀번호 변경 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<MessageResponse> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        session.invalidate();

        userService.delete(username);

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "회원 탈퇴 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
