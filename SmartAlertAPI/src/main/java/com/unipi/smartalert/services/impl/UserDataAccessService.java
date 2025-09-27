package com.unipi.smartalert.services.impl;

import com.unipi.smartalert.models.User;
import com.unipi.smartalert.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class UserDataAccessService {
    private final UserRepository userRepository;

    public UserDataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> selectUserByID(Long id) {
        return userRepository.findById(id);
    }

    public void insertUser(User user) {
        userRepository.save(user);
    }

    public boolean existsPersonWithEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean existsPersonWithId(Long id) {
        return userRepository.existsUserById(id);
    }

    public void updateUserById(User user) {
        userRepository.save(user);
    }

    public Optional<User> selectUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
