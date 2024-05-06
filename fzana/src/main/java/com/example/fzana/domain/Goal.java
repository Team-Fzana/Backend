package com.example.fzana.domain;


import com.example.fzana.dto.GoalRequest;
import com.example.fzana.entity.BaseEntity;
import com.example.fzana.exception.InvalidGoalException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Goal extends BaseEntity {

    private static final int MAX_TITLE_LENGTH = 50; //목표 내용 글자수 제한

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //Member 테이블과 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="title",nullable=false) //목표 내용
    private String title;

    @Column(name="progress",nullable = false) //진행률 (0-100)
    private int progress;

    public Goal(Member member, final String title, final int progress){
        validateTitleLength(title);

        this.member=member;
        this.title=title;
        this.progress=progress; //초기 진행률은 0
    }

    public static Goal createGoal(Member member, GoalRequest goalRequest) {
        return new Goal(
                member,
                goalRequest.getTitle(),
                goalRequest.getProgress()
        );
    }

    public void change(final String title, final int progress) {
        validateTitleLength(title);
        this.title=title;
        this.progress=progress;
    }

    private void validateTitleLength(final String title){
        if(title==null||title.isEmpty()){
            throw new InvalidGoalException("내용을 입력해주세요.");
        }
        if(title.length()>MAX_TITLE_LENGTH){
            throw new InvalidGoalException(String.format("글자수는 %d를 초과할 수 없습니다.",MAX_TITLE_LENGTH));
        }
    }

}
