package com.example.fzana.domain;

import com.example.fzana.dto.IntroduceRequest;
import com.example.fzana.dto.NicknameRequest;
import com.example.fzana.exception.InvalidMemberException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter @Setter
public class Member {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    private static final int MAX_NICK_NAME_LENGTH=100;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nick_name")
    private String nickName;

    @Column
    private String introduce;

    @Column
    private String memberPhoto;

    @Column
    private Boolean active;     // 1: 활동 중, 0: 활동 X

    protected Member() {
    }

    public Member(final String email, final String password, final String nickName,
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
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private void validateNickName(final String nickName) {  //닉네임 길이 검사
        if (nickName.isEmpty() || nickName.length() > MAX_NICK_NAME_LENGTH) {
            throw new InvalidMemberException(String.format("이름은 1자 이상 %d자 이하여야 합니다.",MAX_NICK_NAME_LENGTH));
        }
    }

    // 닉네임 등록 & 수정
    public void updateNickname(NicknameRequest nicknameRequest) {
        this.nickName = nicknameRequest.getNickname();
    }

    // 소개글 등록 & 수정
    public void updateIntroduce(IntroduceRequest introduceRequest) {
        this.introduce = introduceRequest.getIntroduce();
    }

    public void updateProfile(String fileUrl) {
        this.memberPhoto = fileUrl;
    }

    public void updateState(Boolean active) {
        this.active = active;
    }
}
