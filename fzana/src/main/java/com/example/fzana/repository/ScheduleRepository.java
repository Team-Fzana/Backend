package com.example.fzana.repository;

import com.example.fzana.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM schedule WHERE user_id = :userId",
            nativeQuery = true)
    List<Schedule> findByUserId(Long userId);
}
