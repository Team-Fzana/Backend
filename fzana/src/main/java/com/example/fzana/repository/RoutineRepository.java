package com.example.fzana.repository;

import com.example.fzana.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    @Query(value = "SELECT * FROM routine WHERE member_id = :memberId",
            nativeQuery = true)
    List<Routine> findRoutinesByMemberId(Long memberId);
}
