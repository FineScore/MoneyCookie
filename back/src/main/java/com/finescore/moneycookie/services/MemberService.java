package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.MemberInfo;
import com.finescore.moneycookie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInfo checkInfo(MemberInfo loginInfo) {
        MemberInfo savedMember = findByUsername(loginInfo.getUsername());

        if (!loginInfo.getPassword().equals(savedMember.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }

        return savedMember;
    }

    public Boolean checkUsername(String username) {
        return memberRepository.checkUsername(username);
    }

    public void updatePassword(String username, String password) {
        memberRepository.update(username, password);
    }

    public void save(MemberInfo member) {
        memberRepository.save(member);
    }

    public void delete(String username) {
        memberRepository.delete(username);
    }

    private MemberInfo findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
