package com.example.fzana.repository;

import com.example.fzana.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM schedule WHERE member_id = :memberId",
            nativeQuery = true)
    List<Schedule> findByMemberId(Long memberId);

    // 특정 날짜에 대한 멤버의 일정 조회
    @Query(value = "SELECT * FROM schedule WHERE member_id = :memberId AND DATE(this_day) = :date", nativeQuery = true)
    List<Schedule> findByMemberIdAndDate(@Param("memberId") Long memberId, @Param("date") LocalDate date);

}