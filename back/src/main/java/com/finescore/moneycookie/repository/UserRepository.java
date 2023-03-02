package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.LoginUser;
import com.finescore.moneycookie.models.RegisterUser;

import java.util.List;

public interface UserRepository {
    List<LoginUser> findByUsername(String username);

    List<String> findForDuplicateCheck(String username);

    void save(RegisterUser registerUser);

    void update(String username, String newPassword);

    void delete(String username);
}
