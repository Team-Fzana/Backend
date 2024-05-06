package com.example.fzana.dto;

import com.example.fzana.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GoalResponse {
    private Long id;
    private String title; //목표 내용
    private int progress; //목표 진행률


    public static GoalResponse createGoal(Goal goal) {
        return new GoalResponse(
                goal.getId(),
                goal.getTitle(),
                goal.getProgress());
    }
}
