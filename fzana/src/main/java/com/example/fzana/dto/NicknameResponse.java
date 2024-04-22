package com.example.fzana.dto;

import com.example.fzana.domain.Member;
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
