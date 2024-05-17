package com.example.fzana.dto;

import com.example.fzana.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
public class ScheduleResponse {
    private Long id;
    private String content; // 투두 리스트 내용
    private Integer checkStatus; // 진행 상태 -> 2: 하는 중 / 1: 진행 중 / 0: 미 진행
    private LocalDateTime thisDay; // 일정 or todo-list 등록 날짜
    private Date startTime;     // null 값이 아니라면 일정 -> 시작시간
    private Date endTime;
    private String errorMessage; // 추가된 오류 메시지 필드

    // DB에서 가져온 엔티티를 DTO로 반환하는 메서드
    public static ScheduleResponse createSchedule(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getContent(),
                schedule.getCheckStatus(),
                schedule.getThisDay(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                null); // 에러 메시지 필드는 일반적으로 null로 설정
    }

    // 오류 메시지 전용 생성자
    public ScheduleResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
