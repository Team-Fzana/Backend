package com.example.fzana.dto;

import com.example.fzana.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleForm {
    private Long id; // 일정의 id
    private Long userId; // 일정의 부모 id
    private String content; // 투두 리스트 내용
    private int checkStatus; // 진행 상태 -> 2: 하는 중 / 1: 진행 중 / 0: 미 진행
    private LocalDateTime thisDay; // 일정 or todo-list 등록 날짜
    private Date startTime;     // null 값이 아니라면 일정 -> 시작시간
    private Date endTime;       // null 값이 아니라면 일정 -> 종료시간


    public static ScheduleForm createSchedule(Schedule schedule) {
        return new ScheduleForm(schedule.getId(),
                         schedule.getUser().getId(),
                         schedule.getContent(),
                         schedule.getCheckStatus(),
                         schedule.getThisDay(),
                         schedule.getStartTime(),
                         schedule.getEndTime());
    }
}
