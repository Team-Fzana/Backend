package com.example.fzana.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user") // Schedule 테이블과 일대다 관계
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany // Follow 테이블과 일대다 관계
    private List<Follow> follows = new ArrayList<>();

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column
    private String introduce;

    @Column(name = "user_photo")
    private String userPhoto;

    @Enumerated(EnumType.STRING)
    private States state;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    //@Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가 //JPA 2.2이상이면 필요없고 Column만 쓰면됨
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
   // @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
    private LocalDateTime deletedAt;


}
