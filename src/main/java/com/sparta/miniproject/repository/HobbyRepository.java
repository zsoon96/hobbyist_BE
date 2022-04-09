package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
