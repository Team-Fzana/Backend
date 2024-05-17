package com.example.fzana.dto;

import com.example.fzana.domain.Routine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoutineResponse {
    private Long id;
    private String title; // 루틴 제목
    private String day; // 해당 요일
    private LocalDateTime start_time; // 시작 시간
    private LocalDateTime end_time; // 종료 시간


    public static RoutineResponse createRoutine(Routine routine) {
        return new RoutineResponse(
                routine.getId(),
                routine.getTitle(),
                routine.getDay(),
                routine.getStartTime(),
                routine.getEndTime()
        );
    }
}
