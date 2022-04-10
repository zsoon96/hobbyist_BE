package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    List<Hobby> findAllByOrderByModifiedAtDesc();

}
