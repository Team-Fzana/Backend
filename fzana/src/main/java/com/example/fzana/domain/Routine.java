package com.example.fzana.domain;

import com.example.fzana.dto.RoutineRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //Member 테이블과 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String title;

    @Column
    private String day;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    public Routine(Member member, String title, String day, LocalDateTime startTime, LocalDateTime endTime) {
        this.member = member;
        this.title = title;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // DTO 데이터 -> 엔티티
    public static Routine createRoutine(Member member, RoutineRequest routineRequest) {
        return new Routine(
                member,
                routineRequest.getTitle(),
                routineRequest.getDay(),
                routineRequest.getStartTime(),
                routineRequest.getEndTime()
        );
    }
}
