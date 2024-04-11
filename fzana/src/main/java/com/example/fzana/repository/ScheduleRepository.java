package com.example.fzana.repository;

import com.example.fzana.domain.Schedule;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    // 일정 저장
    public Schedule save(Schedule schedule){
        em.persist(schedule);
        return schedule;
    }

    /* 캘린더 조회 기능 구현 때 사용하기
    // 단일 일정 조회
    public Schedule findOne(Long id){
        return em.find(Schedule.class, id);
    }

    // 전체 일정 조회
    public List<Schedule> findAll(Long id){
        return em.createQuery("select s from Schedule s", Schedule.class)
                .getResultList();
    }*/
}
