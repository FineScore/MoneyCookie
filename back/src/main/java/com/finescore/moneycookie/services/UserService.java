package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.User;
import com.finescore.moneycookie.repository.UserRepositoryJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryJdbc userRepositoryJdbc;

    public List<User> findByUsername(String username) {
        return userRepositoryJdbc.findByUsername(username);
    }

    public List<String> findForDuplicateCheck(String username) {
        return userRepositoryJdbc.findForDuplicateCheck(username);
    }

    public Boolean isEqualsPassword(User tryUser, User savedUser) {
        return tryUser.getPassword().equals(savedUser.getPassword());
    }

    public void updatePassword(String username, String password) {
        userRepositoryJdbc.update(username, password);
    }

    public void save(User member) {
        userRepositoryJdbc.save(member);
    }

    public void delete(String username) {
        userRepositoryJdbc.delete(username);
    }
}
