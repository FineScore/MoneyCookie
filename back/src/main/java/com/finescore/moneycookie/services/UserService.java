package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.User;
import com.finescore.moneycookie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<String> findForDuplicateCheck(String username) {
        return userRepository.findForDuplicateCheck(username);
    }

    public Boolean isEqualsPassword(User tryUser, User savedUser) {
        return tryUser.getPassword().equals(savedUser.getPassword());
    }

    public void updatePassword(String username, String password) {
        userRepository.update(username, password);
    }

    public void save(User member) {
        userRepository.save(member);
    }

    public void delete(String username) {
        userRepository.delete(username);
    }
}
