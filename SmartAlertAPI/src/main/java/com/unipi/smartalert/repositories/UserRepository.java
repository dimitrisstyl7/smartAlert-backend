package com.unipi.smartalert.repositories;

import com.unipi.smartalert.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    boolean existsUserByEmail(String email);
    boolean existsUserById(Long id);
    Optional<User> findUserByEmail(String email);

}