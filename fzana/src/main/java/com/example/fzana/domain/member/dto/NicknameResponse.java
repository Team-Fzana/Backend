package com.example.fzana.domain.member.dto;

import com.example.fzana.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameResponse {

    private String nickname;

    public static NicknameResponse createNicknameDto(Member updated) {
        return new NicknameResponse(updated.getNickName());
    }
}
