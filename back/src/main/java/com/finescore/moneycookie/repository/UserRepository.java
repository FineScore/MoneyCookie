package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findByUsername(String username);

    List<String> findForDuplicateCheck(String username);

    void save(User member);

    void update(String username, String newPassword);

    void delete(String username);
}
