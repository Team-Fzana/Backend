package com.example.fzana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineRequest {
    private String title; // 루틴 제목
    private String day; // 해당 요일
    private LocalDateTime startTime; // 시작 시간
    private LocalDateTime endTime; // 종료 시간
}
