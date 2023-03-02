package com.finescore.moneycookie.models;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class RegisterUser {
    @NotBlank(message = "아이디를 입력하지 않았습니다.")
    @Max(value = 10, message = "아이디는 10글자까지 입력 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어, 숫자만 입력 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하지 않았습니다.")
    @Min(value = 8, message = "최소 8자 이상 입력해야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "영어 대소문자, 숫자, 특수 문자가 각각 1개 이상 포함되어야 합니다.")
    private String password;

    public RegisterUser() {
    }
}
