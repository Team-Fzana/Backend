package com.example.fzana.repository;

import com.example.fzana.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal,Long> {
    List<Goal> findByMemberId(Long memberId);
}
