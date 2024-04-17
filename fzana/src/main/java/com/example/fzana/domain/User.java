package com.example.fzana.domain;

import com.example.fzana.exception.InvalidUserException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter @Setter
public class User {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    private static final int MAX_NICK_NAME_LENGTH=100;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @OneToMany(mappedBy = "user") // Schedule 테이블과 일대다 관계
//    private List<Schedule> schedules = new ArrayList<>();
//
//    @OneToMany // Follow 테이블과 일대다 관계
//    private List<Follow> follows = new ArrayList<>();

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nick_name", nullable = false)
    private String nickName;

    @Column
    private String introduce;

//    @Column
//    private String userPhoto;
//
//    @Enumerated(EnumType.STRING)
//    private States state=States.STOP; //기본값 설정? 이렇게 하는 게 맞는지 잘 모르겠다

//    @Column(name = "created_at") //BaseEntity 클래스 추가해도 됨
//    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
//    private LocalDateTime updatedAt;
//
//    @Column(name = "deleted_at")
//    @Temporal(TemporalType.TIMESTAMP) // TemporalType 설정 추가
//    private LocalDateTime deletedAt;

    protected User() {
    }

    public User(final String email, final String password, final String nickName,
                final String introduce) {
        validateEmail(email);
        validateNickName(nickName);

        this.email=email;
        this.password=password;
        this.nickName=nickName;
        this.introduce=introduce;
    }

    private void validateEmail(final String email) {     //이메일 형식 검사
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidUserException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private void validateNickName(final String nickName) {  //닉네임 길이 검사
        if (nickName.isEmpty() || nickName.length() > MAX_NICK_NAME_LENGTH) {
            throw new InvalidUserException(String.format("이름은 1자 이상 %d자 이하여야 합니다.",MAX_NICK_NAME_LENGTH));
        }
    }

}
