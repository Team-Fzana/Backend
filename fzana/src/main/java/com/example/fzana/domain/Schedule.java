package com.example.fzana.domain;

import com.example.fzana.dto.ScheduleRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Member 테이블 ID 와 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

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


    public Schedule(Member member, String content, int checkStatus, LocalDateTime thisDay, Date startTime, Date endTime) {
        this.member = member;
        this.content = content;
        this.thisDay = thisDay;
        this.checkStatus = checkStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // DTO 데이터로 엔티티 생성
    public static Schedule createSchedule(Member member, ScheduleRequest scheduleRequest) {
        return new Schedule(
                member,
                scheduleRequest.getContent(),
                scheduleRequest.getCheckStatus(),
                scheduleRequest.getThisDay(),
                scheduleRequest.getStartTime(),
                scheduleRequest.getEndTime()
                );
    }


    public void patch(ScheduleRequest scheduleRequest) {
        //예외 발생
//        if(this.id != scheduleRequest.getId())
//            throw new IllegalArgumentException("일정 수정 실패!! 잘못된 일정 id가 입력 되었습니다!");

        // 객체 갱신
        // 변경할 일정 내용이 있다면
        this.content = scheduleRequest.getContent(); // 내용 변경

        this.checkStatus = scheduleRequest.getCheckStatus(); // 상태 변경

        this.thisDay = scheduleRequest.getThisDay(); // 등록 날짜 변경
        this.startTime = scheduleRequest.getStartTime(); // 시작 시간 변경
        this.endTime = scheduleRequest.getEndTime(); //종료 시간 변경
    }
}
