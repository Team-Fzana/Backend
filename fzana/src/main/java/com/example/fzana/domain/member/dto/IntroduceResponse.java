package com.example.fzana.domain.member.dto;

import com.example.fzana.domain.member.domain.Member;
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
