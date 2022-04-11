package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
