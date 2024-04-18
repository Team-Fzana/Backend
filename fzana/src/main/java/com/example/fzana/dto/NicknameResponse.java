package com.example.fzana.dto;

import com.example.fzana.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameResponse {

    private String nickname;

    public static NicknameResponse createNicknameDto(User updated) {
        return new NicknameResponse(updated.getNickName());
    }
}
