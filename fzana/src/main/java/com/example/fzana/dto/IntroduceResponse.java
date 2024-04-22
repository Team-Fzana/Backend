package com.example.fzana.dto;

import com.example.fzana.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IntroduceResponse {
    private String introduce;

    public static IntroduceResponse createIntroduceDto(Member updated) {
        return new IntroduceResponse(updated.getIntroduce());
    }
}
