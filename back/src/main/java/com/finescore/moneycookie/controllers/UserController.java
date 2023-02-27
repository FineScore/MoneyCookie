package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.User;
import com.finescore.moneycookie.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "회원 관련 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "아이디 중복 체크")
    @GetMapping("/check")
    public ResponseEntity<MessageResponse> checkDuplicateUsername(String username) {
        List<String> savedUsername = userService.findForDuplicateCheck(username);

        if (savedUsername.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse("SUCCESS", "사용 가능한 아이디");
            log.info("사용 가능한 아이디, 아이디: {}", username);

            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "이미 존재하는 아이디");
            log.info("이미 존재하는 아이디, 아이디: {}", username);

            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody User tryUser, HttpServletRequest request) {
        List<User> savedUser = userService.findByUsername(tryUser.getUsername());

        if (!savedUser.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("username", savedUser.get(0).getUsername());

            Boolean isEqualsPassword = userService.isEqualsPassword(tryUser, savedUser.get(0));

            if (!isEqualsPassword) {
                MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "비밀번호 불일치");
                log.info("비밀번호 불일치");

                return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
            }

            MessageResponse messageResponse = new MessageResponse("SUCCESS", "로그인 성공");
            log.info("로그인 성공");

            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "존재하지 않는 회원");
            log.info("존재하지 않는 회원, 요청 아이디: {}", tryUser.getUsername());

            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "로그아웃 성공");
        log.info("로그아웃 성공");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @Operation(summary = "회원 등록")
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody User user) {
        userService.save(user);

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "회원가입 완료");
        log.info("회원가입 완료, 아이디: {}", user.getUsername());

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 변경")
    @PutMapping("/password")
    public ResponseEntity<MessageResponse> updatePassword(@RequestBody Map<String, String> body, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        userService.updatePassword(username, body.get("password"));

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "비밀번호 변경 완료");
        log.info("비밀번호 변경 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/user")
    public ResponseEntity<MessageResponse> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        session.invalidate();

        userService.delete(username);

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "회원 탈퇴 완료");
        log.info("회원 탈퇴 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
