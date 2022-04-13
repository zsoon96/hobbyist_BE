package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
    User findByHobbies(Hobby hobby);
}
