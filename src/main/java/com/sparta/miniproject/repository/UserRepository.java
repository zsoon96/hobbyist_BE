package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    <T> Optional<T> findByUsername(String username);
}
