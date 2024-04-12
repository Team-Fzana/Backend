package com.example.fzana.domain;

import com.example.fzana.dto.ScheduleForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "todolist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // User 테이블 ID 와 다대일 관계
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;

    @Column
    private LocalDateTime thisDay;

    @Column(name = "check_status") // "check" 대신 "check_status"로 변경
    private int checkStatus;

    @Column(name = "start_time") // 데이터베이스 컬럼명 변경
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private Date startTime;

    @Column(name = "end_time") // 데이터베이스 컬럼명 변경
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private Date endTime;



    @Column(name = "created_at") // 데이터베이스 컬럼명 변경
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private Date createdAt;

    @Column(name = "updated_at") // 데이터베이스 컬럼명 변경
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private Date updatedAt;

    public Schedule(User user, String content, LocalDateTime thisDay, int checkStatus, Date startTime, Date endTime, LocalDateTime now, LocalDateTime now1) {
        this.user = user;
        this.content = content;
        this.thisDay = thisDay;
        this.checkStatus = checkStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static Schedule createSchedule(User user, ScheduleForm scheduleForm) {
        if (scheduleForm.getId() != null)
            throw new IllegalArgumentException("일정 생성 실패! 일정의 id가 없어야 합니다!");
        if (scheduleForm.getUserId() != user.getId())
            throw new IllegalArgumentException("일정 생성 실패! 사용자의 id가 잘못 되었습니다!");

        return new Schedule(
                user,
                scheduleForm.getContent(),
                scheduleForm.getThisDay(),
                scheduleForm.getCheckStatus(),
                scheduleForm.getStartTime(),
                scheduleForm.getEndTime(),
                LocalDateTime.now(),
                LocalDateTime.now()
                );
    }



    public void patch(ScheduleForm scheduleForm) {
        //예외 발생
        if(this.id != scheduleForm.getId())
            throw new IllegalArgumentException("일정 수정 실패!! 잘못된 일정 id가 입력 되었습니다!");

        // 객체 갱신
        // 변경할 일정 내용이 있다면
        this.content = scheduleForm.getContent(); // 내용 변경

        this.checkStatus = scheduleForm.getCheckStatus(); // 상태 변경

        this.thisDay = scheduleForm.getThisDay(); // 등록 날짜 변경
        this.startTime = scheduleForm.getStartTime(); // 시작 시간 변경
        this.endTime = scheduleForm.getEndTime(); //종료 시간 변경
    }
}
