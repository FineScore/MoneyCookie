package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberInfo {
    private String username;
    private String password;

    public MemberInfo() {
    }

    public MemberInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
}