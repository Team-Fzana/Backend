package com.example.fzana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserinfoResponse {
    private String nickname;
    private String introduce;

    public static UserinfoResponse createUserinfoDto(String nickName, String introduce) {
        return new UserinfoResponse(nickName, introduce);
    }
}
