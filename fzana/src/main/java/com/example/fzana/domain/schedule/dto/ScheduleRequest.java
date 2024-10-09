package com.example.fzana.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    private String content; // 투두 리스트 내용
    private int checkStatus; // 진행 상태 -> 2: 하는 중 / 1: 진행 중 / 0: 미 진행
    private LocalDateTime thisDay; // 일정 or todo-list 등록 날짜
    private Date startTime;     // null 값이 아니라면 일정 -> 시작시간
    private Date endTime;       // null 값이 아니라면 일정 -> 종료시간
}
