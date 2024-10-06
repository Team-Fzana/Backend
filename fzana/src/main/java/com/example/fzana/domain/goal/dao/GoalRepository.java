package com.example.fzana.domain.goal.dao;

import com.example.fzana.domain.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal,Long> {
    List<Goal> findByMemberId(Long memberId);
}
