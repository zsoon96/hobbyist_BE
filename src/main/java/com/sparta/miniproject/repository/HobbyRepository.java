package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
