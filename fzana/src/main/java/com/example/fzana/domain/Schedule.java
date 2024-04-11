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



    public Schedule(User user, String content, LocalDateTime thisDay, int checkStatus, Date startTime, Date endTime) {
        this.user = user;
        this.content = content;
        this.thisDay = thisDay;
        this.checkStatus = checkStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
