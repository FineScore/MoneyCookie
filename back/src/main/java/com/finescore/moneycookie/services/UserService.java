package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.LoginUser;
import com.finescore.moneycookie.models.RegisterUser;
import com.finescore.moneycookie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepositoryJdbc;

    public List<LoginUser> findByUsername(String username) {
        return userRepositoryJdbc.findByUsername(username);
    }

    public List<String> findForDuplicateCheck(String username) {
        return userRepositoryJdbc.findForDuplicateCheck(username);
    }

    public Boolean isEqualsPassword(LoginUser tryLoginUser, LoginUser savedLoginUser) {
        return tryLoginUser.getPassword().equals(savedLoginUser.getPassword());
    }

    public void updatePassword(String username, String password) {
        userRepositoryJdbc.update(username, password);
    }

    public void save(RegisterUser member) {
        userRepositoryJdbc.save(member);
    }

    public void delete(String username) {
        userRepositoryJdbc.delete(username);
    }
}
