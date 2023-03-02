package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginUser {
    @NotBlank(message = "아이디를 입력하지 않았습니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하지 않았습니다.")
    private String password;

    public LoginUser() {
    }
}