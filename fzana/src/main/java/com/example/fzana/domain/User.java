package com.example.fzana.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user") // Schedule 테이블과 일대 다 관계
    private List<Schedule> schedules = new ArrayList<>();

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String nickName;
    @Column
    private String introduce;
    @Column
    private String userPhoto;
    @Enumerated(EnumType.STRING)
    private States state;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private LocalDateTime deletedAt;


}
