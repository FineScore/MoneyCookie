package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.LoginUser;
import com.finescore.moneycookie.models.RegisterUser;
import com.finescore.moneycookie.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    public ResponseEntity<MessageResponse> login(@RequestBody @Validated LoginUser tryLoginUser, BindingResult bindingResult, HttpServletRequest request) {
        // 검증 실패
        if (bindingResult.hasErrors()) {
            List<FailedReason> reasons = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                reasons.add(new FailedReason(error.getField(), error.getDefaultMessage()));
            }

            FailedValidateResponse response = new FailedValidateResponse("BAD REQUEST", "잘못된 요청입니다.", reasons);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 검증 성공
        List<LoginUser> savedLoginUser = userService.findByUsername(tryLoginUser.getUsername());

        if (!savedLoginUser.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("username", savedLoginUser.get(0).getUsername());

            Boolean isEqualsPassword = userService.isEqualsPassword(tryLoginUser, savedLoginUser.get(0));

            if (!isEqualsPassword) {
                MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "잘못된 비밀번호입니다.");
                log.info("비밀번호 불일치");

                return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
            }

            MessageResponse messageResponse = new MessageResponse("SUCCESS", "로그인 성공");
            log.info("로그인 성공");

            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            MessageResponse messageResponse = new MessageResponse("BAD REQUEST", "존재하지 않는 회원입니다.");
            log.info("존재하지 않는 회원, 요청 아이디: {}", tryLoginUser.getUsername());

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
    public ResponseEntity<MessageResponse> register(@RequestBody @Validated RegisterUser registerUser, BindingResult bindingResult) {
        // 검증 실패
        if (bindingResult.hasErrors()) {
            List<FailedReason> reasons = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                reasons.add(new FailedReason(error.getField(), error.getDefaultMessage()));
            }

            FailedValidateResponse response = new FailedValidateResponse("BAD REQUEST", "잘못된 요청입니다.", reasons);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 검증 성공
        userService.save(registerUser);

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "회원가입 완료");
        log.info("회원가입 완료, 아이디: {}", registerUser.getUsername());

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 변경")
    @PutMapping("/password")
    public ResponseEntity<MessageResponse> updatePassword(@RequestBody Map<String, String> newPasswordMap, HttpServletRequest request) {
        String newPassword = newPasswordMap.get("password");

        // 검증 실패
        if (!isValidatePassword(newPassword)) {
            List<FailedReason> reasons = new ArrayList<>();
            if (newPassword.isBlank()) {
                reasons.add(new FailedReason("password", "입력된 비밀번호가 없습니다."));
            }
            if (!isPatternMatches(newPassword)) {
                reasons.add(new FailedReason("password", "영어 대소문자, 숫자, 특수 문자가 각각 1개 이상 포함되어야 합니다."));
            }

            if (!isMinOverLength(newPassword)) {
                reasons.add(new FailedReason("password", "최소 8자 이상 입력해야 합니다."));
            }

            FailedValidateResponse response = new FailedValidateResponse("BAD REQUEST", "잘못된 요청입니다.", reasons);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 검증 성공
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        userService.updatePassword(username, newPassword);

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

    private static boolean isValidatePassword(String newPassword) {
        return !newPassword.isBlank() && isPatternMatches(newPassword) && isMinOverLength(newPassword);
    }

    private static boolean isPatternMatches(String newPassword) {
        return newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$");
    }

    private static boolean isMinOverLength(String newPassword) {
        return newPassword.length() > 8;
    }
}
