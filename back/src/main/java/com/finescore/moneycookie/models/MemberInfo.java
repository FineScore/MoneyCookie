package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MemberInfo {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime createDate;

    public MemberInfo() {
    }

    public MemberInfo(String email, String password, String nickname, LocalDateTime createDate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createDate = createDate;
    }
}
